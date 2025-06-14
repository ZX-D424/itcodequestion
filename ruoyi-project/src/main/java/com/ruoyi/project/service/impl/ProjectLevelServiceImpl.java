package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectLevelMapper;
import com.ruoyi.project.domain.ProjectLevel;
import com.ruoyi.project.service.IProjectLevelService;

/**
 * 关卡Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@Service
public class ProjectLevelServiceImpl implements IProjectLevelService 
{
    @Autowired
    private ProjectLevelMapper projectLevelMapper;

    /**
     * 查询关卡
     * 
     * @param id 关卡主键
     * @return 关卡
     */
    @Override
    public ProjectLevel selectProjectLevelById(Long id)
    {
        return projectLevelMapper.selectProjectLevelById(id);
    }

    /**
     * 查询关卡列表
     * 
     * @param projectLevel 关卡
     * @return 关卡
     */
    @Override
    public List<ProjectLevel> selectProjectLevelList(ProjectLevel projectLevel)
    {
        return projectLevelMapper.selectProjectLevelList(projectLevel);
    }

    /**
     * 新增关卡
     * 
     * @param projectLevel 关卡
     * @return 结果
     */
    @Override
    public int insertProjectLevel(ProjectLevel projectLevel)
    {
        projectLevel.setCreateTime(DateUtils.getNowDate());
        return projectLevelMapper.insertProjectLevel(projectLevel);
    }

    /**
     * 修改关卡
     * 
     * @param projectLevel 关卡
     * @return 结果
     */
    @Override
    public int updateProjectLevel(ProjectLevel projectLevel)
    {
        projectLevel.setUpdateTime(DateUtils.getNowDate());
        return projectLevelMapper.updateProjectLevel(projectLevel);
    }

    /**
     * 批量删除关卡
     * 
     * @param ids 需要删除的关卡主键
     * @return 结果
     */
    @Override
    public int deleteProjectLevelByIds(Long[] ids)
    {
        return projectLevelMapper.deleteProjectLevelByIds(ids);
    }

    /**
     * 删除关卡信息
     * 
     * @param id 关卡主键
     * @return 结果
     */
    @Override
    public int deleteProjectLevelById(Long id)
    {
        return projectLevelMapper.deleteProjectLevelById(id);
    }
}
