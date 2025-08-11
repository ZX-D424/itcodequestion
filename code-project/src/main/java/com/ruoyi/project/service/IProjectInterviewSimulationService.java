package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectInterviewSimulation;

/**
 * 面试模拟记录Service接口
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
public interface IProjectInterviewSimulationService 
{
    /**
     * 查询面试模拟记录
     * 
     * @param id 面试模拟记录主键
     * @return 面试模拟记录
     */
    public ProjectInterviewSimulation selectProjectInterviewSimulationById(Long id);

    /**
     * 查询面试模拟记录列表
     * 
     * @param projectInterviewSimulation 面试模拟记录
     * @return 面试模拟记录集合
     */
    public List<ProjectInterviewSimulation> selectProjectInterviewSimulationList(ProjectInterviewSimulation projectInterviewSimulation);

    /**
     * 新增面试模拟记录
     * 
     * @param projectInterviewSimulation 面试模拟记录
     * @return 结果
     */
    public int insertProjectInterviewSimulation(ProjectInterviewSimulation projectInterviewSimulation);

    /**
     * 修改面试模拟记录
     * 
     * @param projectInterviewSimulation 面试模拟记录
     * @return 结果
     */
    public int updateProjectInterviewSimulation(ProjectInterviewSimulation projectInterviewSimulation);

    /**
     * 批量删除面试模拟记录
     * 
     * @param ids 需要删除的面试模拟记录主键集合
     * @return 结果
     */
    public int deleteProjectInterviewSimulationByIds(Long[] ids);

    /**
     * 删除面试模拟记录信息
     * 
     * @param id 面试模拟记录主键
     * @return 结果
     */
    public int deleteProjectInterviewSimulationById(Long id);
}
