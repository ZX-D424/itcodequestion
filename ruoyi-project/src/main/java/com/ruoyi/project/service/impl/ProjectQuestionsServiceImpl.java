package com.ruoyi.project.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
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
}
