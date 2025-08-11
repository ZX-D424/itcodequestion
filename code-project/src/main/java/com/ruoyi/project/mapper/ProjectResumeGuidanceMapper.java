package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProjectResumeGuidance;

/**
 * 简历指导记录Mapper接口
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
public interface ProjectResumeGuidanceMapper 
{
    /**
     * 查询简历指导记录
     * 
     * @param id 简历指导记录主键
     * @return 简历指导记录
     */
    public ProjectResumeGuidance selectProjectResumeGuidanceById(Long id);

    /**
     * 查询简历指导记录列表
     * 
     * @param projectResumeGuidance 简历指导记录
     * @return 简历指导记录集合
     */
    public List<ProjectResumeGuidance> selectProjectResumeGuidanceList(ProjectResumeGuidance projectResumeGuidance);

    /**
     * 新增简历指导记录
     * 
     * @param projectResumeGuidance 简历指导记录
     * @return 结果
     */
    public int insertProjectResumeGuidance(ProjectResumeGuidance projectResumeGuidance);

    /**
     * 修改简历指导记录
     * 
     * @param projectResumeGuidance 简历指导记录
     * @return 结果
     */
    public int updateProjectResumeGuidance(ProjectResumeGuidance projectResumeGuidance);

    /**
     * 删除简历指导记录
     * 
     * @param id 简历指导记录主键
     * @return 结果
     */
    public int deleteProjectResumeGuidanceById(Long id);

    /**
     * 批量删除简历指导记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectResumeGuidanceByIds(Long[] ids);
}
