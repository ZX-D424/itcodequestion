package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectModuleMapper;
import com.ruoyi.project.domain.ProjectModule;
import com.ruoyi.project.service.IProjectModuleService;

/**
 * 项目模块Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-12
 */
@Service
public class ProjectModuleServiceImpl implements IProjectModuleService 
{
    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    /**
     * 查询项目模块
     * 
     * @param id 项目模块主键
     * @return 项目模块
     */
    @Override
    public ProjectModule selectProjectModuleById(Long id)
    {
        return projectModuleMapper.selectProjectModuleById(id);
    }

    /**
     * 查询项目模块列表
     * 
     * @param projectModule 项目模块
     * @return 项目模块
     */
    @Override
    public List<ProjectModule> selectProjectModuleList(ProjectModule projectModule)
    {
        return projectModuleMapper.selectProjectModuleList(projectModule);
    }

    /**
     * 新增项目模块
     * 
     * @param projectModule 项目模块
     * @return 结果
     */
    @Override
    public int insertProjectModule(ProjectModule projectModule)
    {
        projectModule.setCreateTime(DateUtils.getNowDate());
        return projectModuleMapper.insertProjectModule(projectModule);
    }

    /**
     * 修改项目模块
     * 
     * @param projectModule 项目模块
     * @return 结果
     */
    @Override
    public int updateProjectModule(ProjectModule projectModule)
    {
        projectModule.setUpdateTime(DateUtils.getNowDate());
        return projectModuleMapper.updateProjectModule(projectModule);
    }

    /**
     * 批量删除项目模块
     * 
     * @param ids 需要删除的项目模块主键
     * @return 结果
     */
    @Override
    public int deleteProjectModuleByIds(Long[] ids)
    {
        return projectModuleMapper.deleteProjectModuleByIds(ids);
    }

    /**
     * 删除项目模块信息
     * 
     * @param id 项目模块主键
     * @return 结果
     */
    @Override
    public int deleteProjectModuleById(Long id)
    {
        return projectModuleMapper.deleteProjectModuleById(id);
    }
}
