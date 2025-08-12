package com.ruoyi.project.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.email.HtmlEmailService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.domain.ProjectScoreRank;
import com.ruoyi.project.mapper.ProjectModuleMapper;
import com.ruoyi.project.mapper.ProjectScoreRankMapper;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectQuestionsMapper;
import com.ruoyi.project.domain.ProjectQuestions;
import com.ruoyi.project.service.IProjectQuestionsService;

import javax.annotation.Resource;

/**
 * 答题评分Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Slf4j
@Service
public class ProjectQuestionsServiceImpl implements IProjectQuestionsService 
{
    @Autowired
    private ProjectQuestionsMapper projectQuestionsMapper;

    @Autowired
    private ProjectScoreRankMapper projectScoreRankMapper;

    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    @Resource
    private HtmlEmailService htmlEmailService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询答题评分
     * 
     * @param id 答题评分主键
     * @return 答题评分
     */
    @Override
    public ProjectQuestions selectProjectQuestionsById(Long id)
    {
        return projectQuestionsMapper.selectProjectQuestionsById(id);
    }

    /**
     * 查询答题评分列表
     * 
     * @param projectQuestions 答题评分
     * @return 答题评分
     */
    @Override
    public List<ProjectQuestions> selectProjectQuestionsList(ProjectQuestions projectQuestions)
    {
        return projectQuestionsMapper.selectProjectQuestionsList(projectQuestions);
    }

    /**
     * 新增答题评分
     * 
     * @param projectQuestions 答题评分
     * @return 结果
     */
    @Override
    public AjaxResult insertProjectQuestions(ProjectQuestions projectQuestions){

        //判断是否已存在
        Long id = projectQuestionsMapper.selectProjectQuestionsByUserIdAndLevelId(projectQuestions.getModuleId(),projectQuestions.getUserId(),projectQuestions.getLevelId());
        if(ObjectUtils.isEmpty( id)){
           int rows =  projectQuestionsMapper.insertProjectQuestions(projectQuestions);
           if (rows > 0){
               return  AjaxResult.success("操作成功") ;
           }
           return  AjaxResult.error("操作失败") ;
        }
        return AjaxResult.error("不允许重复闯关");
    }

    /**
     * 修改答题评分
     * 
     * @param projectQuestions 答题评分
     * @return 结果
     */
    @Override
    public int updateProjectQuestions(ProjectQuestions projectQuestions)
    {
        return projectQuestionsMapper.updateProjectQuestions(projectQuestions);
    }

    /**
     * 批量删除答题评分
     * 
     * @param ids 需要删除的答题评分主键
     * @return 结果
     */
    @Override
    public int deleteProjectQuestionsByIds(Long[] ids)
    {
        return projectQuestionsMapper.deleteProjectQuestionsByIds(ids);
    }

    /**
     * 删除答题评分信息
     * 
     * @param id 答题评分主键
     * @return 结果
     */
    @Override
    public int deleteProjectQuestionsById(Long id)
    {
        return projectQuestionsMapper.deleteProjectQuestionsById(id);
    }

    @Override
    public int getQuartzScore() {
        // 查询完成最后一关且得到评分的答题
        List<ProjectQuestions> projectQuestions = projectQuestionsMapper.selectUsersCompletedLastLevel();
        if (projectQuestions == null || projectQuestions.isEmpty()) {
            return 0;
        }

        int processedCount = 0;
        for (ProjectQuestions projectQuestion : projectQuestions) {
            Long userId = projectQuestion.getUserId();
            Long moduleId = projectQuestion.getModuleId();
            try {
                // 判断是否未统计过
                ProjectScoreRank count = selectModuleScore(userId, moduleId);
                if (count == null) {
                    // 统计该用户的该模块总分
                    double moduleScore = getModuleScore(userId, moduleId);

                    ProjectScoreRank projectScoreRank = new ProjectScoreRank();
                    projectScoreRank.setUserId(userId);
                    projectScoreRank.setModuleId(moduleId);
                    projectScoreRank.setScore(moduleScore);
                    projectScoreRank.setCreateTime(new Date());
                    projectScoreRank.setScoreTime(new Date());

                    projectScoreRankMapper.insertProjectScoreRank(projectScoreRank);

                    // 通知用户得分与排名
                    String email = userService.selectUserById(userId).getEmail();
                    Long rank = projectScoreRankMapper.getRank(userId, moduleId);
                    String userName = userService.selectUserById(userId).getUserName();
                    String moduleName = projectModuleMapper.selectProjectModuleById(moduleId).getName();
                    sendRank(email,userName,moduleName,moduleScore,rank);
                    
                    processedCount++;
                }
            } catch (Exception e) {
                log.error("处理用户[{}]模块[{}]得分统计异常",
                        projectQuestion.getUserId(),
                        projectQuestion.getModuleId(), e);
            }
        }
        return processedCount;
    }

    // 统计用户模块得分
    public double getModuleScore(Long userId, Long moduleId){
        Double score = projectQuestionsMapper.getModuleScoreByUserId(userId, moduleId);
        return score == null ? 0.0 : score;
    }

    // 查询是否统计
    public ProjectScoreRank selectModuleScore(Long userId, Long moduleId){
        return projectScoreRankMapper.selectModuleScoreByUserId(userId, moduleId);
    }

    // 邮件通知
    public void sendRank(String email, String userName, String moduleName, Double moduleScore, Long rank) throws Exception {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(userName) ||
                StringUtils.isEmpty(moduleName) || moduleScore == null || rank == null) {
            throw new IllegalArgumentException("邮件发送参数不能为空，请检查输入");
        }
        String subject = "湖南星云科技" + moduleName + "排名通知";
        String emailContent = "<div style='text-align: center; font-family: Arial, sans-serif;'>" +
                "<img src='code-ui/src/assets/logo/logo.png' alt='湖南星云科技logo' width='100px' height='100px' style='margin: 20px 0;'>" +
                "<h1 style='color: #333;'>湖南星云科技模块排名通知</h1>" +
                "<p style='font-size: 16px; line-height: 1.6;'>尊敬的用户：" + userName + "，您好！</p>" +
                "<p style='font-size: 16px; line-height: 1.6;'>您在 <strong>" + moduleName + "</strong> 的表现如下：</p>" +
                "<p style='font-size: 16px; line-height: 1.6;'>" +
                "模块得分：<span style='color: #e53e3e; font-weight: bold; font-size: 18px;'>" + moduleScore + "</span><br>" +
                "当前排名：<span style='color: #2563eb; font-weight: bold; font-size: 18px;'>第" + rank + "名</span>" +
                "</p>" +
                "<p style='font-size: 14px; color: #666; margin-top: 20px;'>请持续关注您的排名变化，感谢使用！</p>" +
                "</div>";
        try {
            htmlEmailService.sendHtmlMessage(email, subject, emailContent);
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败：" + e.getMessage(), e);
        }
    }


}
