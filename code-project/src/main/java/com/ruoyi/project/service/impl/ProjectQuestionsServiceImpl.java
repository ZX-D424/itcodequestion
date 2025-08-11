package com.ruoyi.project.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.project.domain.ProjectScoreRank;
import com.ruoyi.project.mapper.ProjectScoreRankMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectQuestionsMapper;
import com.ruoyi.project.domain.ProjectQuestions;
import com.ruoyi.project.service.IProjectQuestionsService;

/**
 * 答题评分Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Service
public class ProjectQuestionsServiceImpl implements IProjectQuestionsService 
{
    @Autowired
    private ProjectQuestionsMapper projectQuestionsMapper;

    @Autowired
    private ProjectScoreRankMapper projectScoreRankMapper;

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

    public int getQuartzScore() {
        // 1. 获取前一天日期
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 2. 查询完成最后一关且得到评分的用户
        List<ProjectQuestions> projectQuestions = projectQuestionsMapper.selectUsersCompletedLastLevel(yesterday);
        if (projectQuestions.isEmpty()) {
            return 0;
        }

        int processedCount = 0;
        for (ProjectQuestions projectQuestion : projectQuestions) {
            // 统计该用户的该模块总分
            ProjectScoreRank projectScoreRank = new ProjectScoreRank();
            projectScoreRank.setUserId(projectQuestion.getUserId());
            projectScoreRank.setModuleId(projectQuestion.getModuleId());
            projectScoreRank.setScore(getModuleScore(projectQuestion.getUserId(),projectQuestion.getModuleId()));
            projectScoreRankMapper.insertProjectScoreRank(projectScoreRank);
            // 更新用户总得分
            projectScoreRank.setTotalScore(getUserTotalScore(projectQuestion.getUserId()));
            projectScoreRankMapper.updateProjectScoreRank(projectScoreRank);
        }

        return processedCount;
    }

    // 统计用户模块得分
    public Double getModuleScore(Long userId, Long moduleId){
        return projectQuestionsMapper.getModuleScoreByUserId(userId, moduleId);
    }

    // 统计用户总得分
    public Double getUserTotalScore(Long userId){
        return projectQuestionsMapper.getUserTotalScore(userId);
    }
}
