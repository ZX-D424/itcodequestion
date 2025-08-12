package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectResumeGuidanceMapper;
import com.ruoyi.project.domain.ProjectResumeGuidance;
import com.ruoyi.project.service.IProjectResumeGuidanceService;

/**
 * 简历指导记录Service业务层处理
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
@Service
public class ProjectResumeGuidanceServiceImpl implements IProjectResumeGuidanceService 
{
    @Autowired
    private ProjectResumeGuidanceMapper projectResumeGuidanceMapper;

    /**
     * 查询简历指导记录
     * 
     * @param id 简历指导记录主键
     * @return 简历指导记录
     */
    @Override
    public ProjectResumeGuidance selectProjectResumeGuidanceById(Long id)
    {
        return projectResumeGuidanceMapper.selectProjectResumeGuidanceById(id);
    }

    /**
     * 查询简历指导记录列表
     * 
     * @param projectResumeGuidance 简历指导记录
     * @return 简历指导记录
     */
    @Override
    public List<ProjectResumeGuidance> selectProjectResumeGuidanceList(ProjectResumeGuidance projectResumeGuidance)
    {
        return projectResumeGuidanceMapper.selectProjectResumeGuidanceList(projectResumeGuidance);
    }

    /**
     * 新增简历指导记录
     * 
     * @param projectResumeGuidance 简历指导记录
     * @return 结果
     */
    @Override
    public int insertProjectResumeGuidance(ProjectResumeGuidance projectResumeGuidance)
    {
        projectResumeGuidance.setCreateTime(DateUtils.getNowDate());
        return projectResumeGuidanceMapper.insertProjectResumeGuidance(projectResumeGuidance);
    }

    /**
     * 修改简历指导记录
     * 
     * @param projectResumeGuidance 简历指导记录
     * @return 结果
     */
    @Override
    public int updateProjectResumeGuidance(ProjectResumeGuidance projectResumeGuidance)
    {
        projectResumeGuidance.setUpdateTime(DateUtils.getNowDate());
        return projectResumeGuidanceMapper.updateProjectResumeGuidance(projectResumeGuidance);
    }

    /**
     * 批量删除简历指导记录
     * 
     * @param ids 需要删除的简历指导记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectResumeGuidanceByIds(Long[] ids)
    {
        return projectResumeGuidanceMapper.deleteProjectResumeGuidanceByIds(ids);
    }

    /**
     * 删除简历指导记录信息
     * 
     * @param id 简历指导记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectResumeGuidanceById(Long id)
    {
        return projectResumeGuidanceMapper.deleteProjectResumeGuidanceById(id);
    }
}
