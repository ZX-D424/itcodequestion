package com.ruoyi.project.service.impl;

import java.util.List;
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
    public int insertProjectQuestions(ProjectQuestions projectQuestions)
    {
        return projectQuestionsMapper.insertProjectQuestions(projectQuestions);
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
