package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProjectInterviewSimulation;

/**
 * 面试模拟记录Mapper接口
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
public interface ProjectInterviewSimulationMapper 
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
     * 删除面试模拟记录
     * 
     * @param id 面试模拟记录主键
     * @return 结果
     */
    public int deleteProjectInterviewSimulationById(Long id);

    /**
     * 批量删除面试模拟记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectInterviewSimulationByIds(Long[] ids);
}
