package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectPracticeMapper;
import com.ruoyi.project.domain.ProjectPractice;
import com.ruoyi.project.service.IProjectPracticeService;

/**
 * 练习项目Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@Service
public class ProjectPracticeServiceImpl implements IProjectPracticeService 
{
    @Autowired
    private ProjectPracticeMapper projectPracticeMapper;

    /**
     * 查询练习项目
     * 
     * @param id 练习项目主键
     * @return 练习项目
     */
    @Override
    public ProjectPractice selectProjectPracticeById(Long id)
    {
        ProjectPractice practice = projectPracticeMapper.selectProjectPracticeById(id);
        return practice;
    }

    /**
     * 查询练习项目列表
     * 
     * @param projectPractice 练习项目
     * @return 练习项目
     */
    @Override
    public List<ProjectPractice> selectProjectPracticeList(ProjectPractice projectPractice)
    {
        return projectPracticeMapper.selectProjectPracticeList(projectPractice);
    }

    /**
     * 新增练习项目
     * 
     * @param projectPractice 练习项目
     * @return 结果
     */
    @Override
    public int insertProjectPractice(ProjectPractice projectPractice)
    {
        projectPractice.setCreateTime(DateUtils.getNowDate());
        return projectPracticeMapper.insertProjectPractice(projectPractice);
    }

    /**
     * 修改练习项目
     * 
     * @param projectPractice 练习项目
     * @return 结果
     */
    @Override
    public int updateProjectPractice(ProjectPractice projectPractice)
    {
        projectPractice.setUpdateTime(DateUtils.getNowDate());
        return projectPracticeMapper.updateProjectPractice(projectPractice);
    }

    /**
     * 批量删除练习项目
     * 
     * @param ids 需要删除的练习项目主键
     * @return 结果
     */
    @Override
    public int deleteProjectPracticeByIds(Long[] ids)
    {
        return projectPracticeMapper.deleteProjectPracticeByIds(ids);
    }

    /**
     * 删除练习项目信息
     * 
     * @param id 练习项目主键
     * @return 结果
     */
    @Override
    public int deleteProjectPracticeById(Long id)
    {
        return projectPracticeMapper.deleteProjectPracticeById(id);
    }
}
