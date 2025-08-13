package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProjectLevel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关卡Mapper接口
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@Mapper
public interface ProjectLevelMapper 
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
     * 删除关卡
     * 
     * @param id 关卡主键
     * @return 结果
     */
    public int deleteProjectLevelById(Long id);

    /**
     * 批量删除关卡
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectLevelByIds(Long[] ids);

    /**
     * 根据模块ID和关卡序号查询对应的关卡ID
     *
     * @param projectLevel 关卡
     * @return 关卡集合
     */
    public ProjectLevel selectProjectLevelModuleIDAndLevelCode(ProjectLevel projectLevel);

}
