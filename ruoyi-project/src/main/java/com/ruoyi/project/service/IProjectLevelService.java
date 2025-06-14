package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectLevel;

/**
 * 关卡Service接口
 * 
 * @author xuexin
 * @date 2025-06-14
 */
public interface IProjectLevelService 
{
    /**
     * 查询关卡
     * 
     * @param id 关卡主键
     * @return 关卡
     */
    public ProjectLevel selectProjectLevelById(Long id);

    /**
     * 查询关卡列表
     * 
     * @param projectLevel 关卡
     * @return 关卡集合
     */
    public List<ProjectLevel> selectProjectLevelList(ProjectLevel projectLevel);

    /**
     * 新增关卡
     * 
     * @param projectLevel 关卡
     * @return 结果
     */
    public int insertProjectLevel(ProjectLevel projectLevel);

    /**
     * 修改关卡
     * 
     * @param projectLevel 关卡
     * @return 结果
     */
    public int updateProjectLevel(ProjectLevel projectLevel);

    /**
     * 批量删除关卡
     * 
     * @param ids 需要删除的关卡主键集合
     * @return 结果
     */
    public int deleteProjectLevelByIds(Long[] ids);

    /**
     * 删除关卡信息
     * 
     * @param id 关卡主键
     * @return 结果
     */
    public int deleteProjectLevelById(Long id);
}
