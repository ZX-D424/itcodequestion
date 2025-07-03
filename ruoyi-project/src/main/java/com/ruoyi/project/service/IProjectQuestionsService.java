package com.ruoyi.project.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.project.domain.ProjectQuestions;

/**
 * 答题评分Service接口
 * 
 * @author xuexin
 * @date 2025-06-28
 */
public interface IProjectQuestionsService 
{
    /**
     * 查询答题评分
     * 
     * @param id 答题评分主键
     * @return 答题评分
     */
    public ProjectQuestions selectProjectQuestionsById(Long id);

    /**
     * 查询答题评分列表
     * 
     * @param projectQuestions 答题评分
     * @return 答题评分集合
     */
    public List<ProjectQuestions> selectProjectQuestionsList(ProjectQuestions projectQuestions);

    /**
     * 新增答题评分
     * 
     * @param projectQuestions 答题评分
     * @return 结果
     */
    public AjaxResult insertProjectQuestions(ProjectQuestions projectQuestions);

    /**
     * 修改答题评分
     * 
     * @param projectQuestions 答题评分
     * @return 结果
     */
    public int updateProjectQuestions(ProjectQuestions projectQuestions);

    /**
     * 批量删除答题评分
     * 
     * @param ids 需要删除的答题评分主键集合
     * @return 结果
     */
    public int deleteProjectQuestionsByIds(Long[] ids);

    /**
     * 删除答题评分信息
     * 
     * @param id 答题评分主键
     * @return 结果
     */
    public int deleteProjectQuestionsById(Long id);
}
