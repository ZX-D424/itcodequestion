package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectPractice;

/**
 * 练习项目Service接口
 * 
 * @author xuexin
 * @date 2025-06-14
 */
public interface IProjectPracticeService 
{
    /**
     * 查询练习项目
     * 
     * @param id 练习项目主键
     * @return 练习项目
     */
    public ProjectPractice selectProjectPracticeById(Long id);

    /**
     * 查询练习项目列表
     * 
     * @param projectPractice 练习项目
     * @return 练习项目集合
     */
    public List<ProjectPractice> selectProjectPracticeList(ProjectPractice projectPractice);

    /**
     * 新增练习项目
     * 
     * @param projectPractice 练习项目
     * @return 结果
     */
    public int insertProjectPractice(ProjectPractice projectPractice);

    /**
     * 修改练习项目
     * 
     * @param projectPractice 练习项目
     * @return 结果
     */
    public int updateProjectPractice(ProjectPractice projectPractice);

    /**
     * 批量删除练习项目
     * 
     * @param ids 需要删除的练习项目主键集合
     * @return 结果
     */
    public int deleteProjectPracticeByIds(Long[] ids);

    /**
     * 删除练习项目信息
     * 
     * @param id 练习项目主键
     * @return 结果
     */
    public int deleteProjectPracticeById(Long id);
}
