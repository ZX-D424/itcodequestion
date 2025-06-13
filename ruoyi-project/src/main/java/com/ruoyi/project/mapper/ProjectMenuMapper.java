package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProjectMenu;

/**
 * 项目菜单Mapper接口
 * 
 * @author xuexin
 * @date 2025-06-12
 */
public interface ProjectMenuMapper 
{
    /**
     * 查询项目菜单
     * 
     * @param id 项目菜单主键
     * @return 项目菜单
     */
    public ProjectMenu selectProjectMenuById(Long id);

    /**
     * 查询项目菜单列表
     * 
     * @param projectMenu 项目菜单
     * @return 项目菜单集合
     */
    public List<ProjectMenu> selectProjectMenuList(ProjectMenu projectMenu);

    /**
     * 查询项目菜单列表
     *
     * @return 项目菜单集合
     */
    public List<ProjectMenu> getMenuDataList();



    /**
     * 新增项目菜单
     * 
     * @param projectMenu 项目菜单
     * @return 结果
     */
    public int insertProjectMenu(ProjectMenu projectMenu);

    /**
     * 修改项目菜单
     * 
     * @param projectMenu 项目菜单
     * @return 结果
     */
    public int updateProjectMenu(ProjectMenu projectMenu);

    /**
     * 删除项目菜单
     * 
     * @param id 项目菜单主键
     * @return 结果
     */
    public int deleteProjectMenuById(Long id);

    /**
     * 批量删除项目菜单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectMenuByIds(Long[] ids);
}
