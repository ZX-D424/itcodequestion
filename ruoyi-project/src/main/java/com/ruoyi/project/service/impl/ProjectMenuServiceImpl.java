package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectMenuMapper;
import com.ruoyi.project.domain.ProjectMenu;
import com.ruoyi.project.service.IProjectMenuService;

/**
 * 项目菜单Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-12
 */
@Service
public class ProjectMenuServiceImpl implements IProjectMenuService 
{
    @Autowired
    private ProjectMenuMapper projectMenuMapper;

    /**
     * 查询项目菜单
     * 
     * @param id 项目菜单主键
     * @return 项目菜单
     */
    @Override
    public ProjectMenu selectProjectMenuById(Long id)
    {
        return projectMenuMapper.selectProjectMenuById(id);
    }

    /**
     * 查询项目菜单列表
     * 
     * @param projectMenu 项目菜单
     * @return 项目菜单
     */
    @Override
    public List<ProjectMenu> selectProjectMenuList(ProjectMenu projectMenu)
    {
        return projectMenuMapper.selectProjectMenuList(projectMenu);
    }

    /**
     * 新增项目菜单
     * 
     * @param projectMenu 项目菜单
     * @return 结果
     */
    @Override
    public int insertProjectMenu(ProjectMenu projectMenu)
    {
        return projectMenuMapper.insertProjectMenu(projectMenu);
    }

    /**
     * 修改项目菜单
     * 
     * @param projectMenu 项目菜单
     * @return 结果
     */
    @Override
    public int updateProjectMenu(ProjectMenu projectMenu)
    {
        projectMenu.setUpdateTime(DateUtils.getNowDate());
        return projectMenuMapper.updateProjectMenu(projectMenu);
    }

    /**
     * 批量删除项目菜单
     * 
     * @param ids 需要删除的项目菜单主键
     * @return 结果
     */
    @Override
    public int deleteProjectMenuByIds(Long[] ids)
    {
        return projectMenuMapper.deleteProjectMenuByIds(ids);
    }

    /**
     * 删除项目菜单信息
     * 
     * @param id 项目菜单主键
     * @return 结果
     */
    @Override
    public int deleteProjectMenuById(Long id)
    {
        return projectMenuMapper.deleteProjectMenuById(id);
    }
}
