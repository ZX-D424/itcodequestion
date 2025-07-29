package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectModule;

/**
 * 项目模块Service接口
 * 
 * @author xuexin
 * @date 2025-06-12
 */
public interface IProjectModuleService 
{
    /**
     * 查询项目模块
     * 
     * @param id 项目模块主键
     * @return 项目模块
     */
    public ProjectModule selectProjectModuleById(Long id);

    /**
     * 查询项目模块列表
     * 
     * @param projectModule 项目模块
     * @return 项目模块集合
     */
    public List<ProjectModule> selectProjectModuleList(ProjectModule projectModule);

    /**
     * 新增项目模块
     * 
     * @param projectModule 项目模块
     * @return 结果
     */
    public int insertProjectModule(ProjectModule projectModule);

    /**
     * 修改项目模块
     * 
     * @param projectModule 项目模块
     * @return 结果
     */
    public int updateProjectModule(ProjectModule projectModule);

    /**
     * 批量删除项目模块
     * 
     * @param ids 需要删除的项目模块主键集合
     * @return 结果
     */
    public int deleteProjectModuleByIds(Long[] ids);

    /**
     * 删除项目模块信息
     * 
     * @param id 项目模块主键
     * @return 结果
     */
    public int deleteProjectModuleById(Long id);

    /**
     * 查询项目模块
     *
     * @return 项目模块
     */
    public List<ProjectModule> getModuleDataList(Long type);


    /**
     * 查询项目模块
     *
     * @return 项目模块
     */
    public List<ProjectModule> getModuleDataListByMenuId(Long menuId);



}
