package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectInterviewSimulationMapper;
import com.ruoyi.project.domain.ProjectInterviewSimulation;
import com.ruoyi.project.service.IProjectInterviewSimulationService;

/**
 * 面试模拟记录Service业务层处理
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
@Service
public class ProjectInterviewSimulationServiceImpl implements IProjectInterviewSimulationService 
{
    @Autowired
    private ProjectInterviewSimulationMapper projectInterviewSimulationMapper;

    /**
     * 查询面试模拟记录
     * 
     * @param id 面试模拟记录主键
     * @return 面试模拟记录
     */
    @Override
    public ProjectInterviewSimulation selectProjectInterviewSimulationById(Long id)
    {
        return projectInterviewSimulationMapper.selectProjectInterviewSimulationById(id);
    }

    /**
     * 查询面试模拟记录列表
     * 
     * @param projectInterviewSimulation 面试模拟记录
     * @return 面试模拟记录
     */
    @Override
    public List<ProjectInterviewSimulation> selectProjectInterviewSimulationList(ProjectInterviewSimulation projectInterviewSimulation)
    {
        return projectInterviewSimulationMapper.selectProjectInterviewSimulationList(projectInterviewSimulation);
    }

    /**
     * 新增面试模拟记录
     * 
     * @param projectInterviewSimulation 面试模拟记录
     * @return 结果
     */
    @Override
    public int insertProjectInterviewSimulation(ProjectInterviewSimulation projectInterviewSimulation)
    {
        projectInterviewSimulation.setCreateTime(DateUtils.getNowDate());
        return projectInterviewSimulationMapper.insertProjectInterviewSimulation(projectInterviewSimulation);
    }

    /**
     * 修改面试模拟记录
     * 
     * @param projectInterviewSimulation 面试模拟记录
     * @return 结果
     */
    @Override
    public int updateProjectInterviewSimulation(ProjectInterviewSimulation projectInterviewSimulation)
    {
        projectInterviewSimulation.setUpdateTime(DateUtils.getNowDate());
        return projectInterviewSimulationMapper.updateProjectInterviewSimulation(projectInterviewSimulation);
    }

    /**
     * 批量删除面试模拟记录
     * 
     * @param ids 需要删除的面试模拟记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectInterviewSimulationByIds(Long[] ids)
    {
        return projectInterviewSimulationMapper.deleteProjectInterviewSimulationByIds(ids);
    }

    /**
     * 删除面试模拟记录信息
     * 
     * @param id 面试模拟记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectInterviewSimulationById(Long id)
    {
        return projectInterviewSimulationMapper.deleteProjectInterviewSimulationById(id);
    }
}
