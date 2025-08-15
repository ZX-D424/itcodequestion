package com.ruoyi.project.service.impl;

import java.util.Collections;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectScoreRankMapper;
import com.ruoyi.project.domain.ProjectScoreRank;
import com.ruoyi.project.service.IProjectScoreRankService;

/**
 * 得分排名Service业务层处理
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Service
public class ProjectScoreRankServiceImpl implements IProjectScoreRankService 
{
    @Autowired
    private ProjectScoreRankMapper projectScoreRankMapper;

    /**
     * 查询得分排名
     * 
     * @param id 得分排名主键
     * @return 得分排名
     */
    @Override
    public ProjectScoreRank selectProjectScoreRankById(Long id)
    {
        return projectScoreRankMapper.selectProjectScoreRankById(id);
    }

    /**
     * 查询得分排名列表
     * 
     * @param projectScoreRank 得分排名
     * @return 得分排名
     */
    @Override
    public List<ProjectScoreRank> selectProjectScoreRankList(ProjectScoreRank projectScoreRank)
    {
        return projectScoreRankMapper.selectProjectScoreRankList(projectScoreRank);
    }

    /**
     * 新增得分排名
     * 
     * @param projectScoreRank 得分排名
     * @return 结果
     */
    @Override
    public int insertProjectScoreRank(ProjectScoreRank projectScoreRank)
    {
        projectScoreRank.setCreateTime(DateUtils.getNowDate());
        return projectScoreRankMapper.insertProjectScoreRank(projectScoreRank);
    }

    /**
     * 修改得分排名
     * 
     * @param projectScoreRank 得分排名
     * @return 结果
     */
    @Override
    public int updateProjectScoreRank(ProjectScoreRank projectScoreRank)
    {
        projectScoreRank.setUpdateTime(DateUtils.getNowDate());
        return projectScoreRankMapper.updateProjectScoreRank(projectScoreRank);
    }

    /**
     * 批量删除得分排名
     * 
     * @param ids 需要删除的得分排名主键
     * @return 结果
     */
    @Override
    public int deleteProjectScoreRankByIds(Long[] ids)
    {
        return projectScoreRankMapper.deleteProjectScoreRankByIds(ids);
    }

    /**
     * 删除得分排名信息
     * 
     * @param id 得分排名主键
     * @return 结果
     */
    @Override
    public int deleteProjectScoreRankById(Long id)
    {
        return projectScoreRankMapper.deleteProjectScoreRankById(id);
    }

    @Override
    public List<ProjectScoreRank> selectProjectModuleRankList(Long moduleId) {
        return projectScoreRankMapper.selectProjectModuleRankList(moduleId);
    }

    @Override
    public List<ProjectScoreRank> selectProjectTotalRankList() {
        return projectScoreRankMapper.selectProjectTotalRankList();
    }

    @Override
    public ProjectScoreRank getPersonalRank(Long userId,Long moduleId) {
        return projectScoreRankMapper.getPersonalModuleRank(userId,moduleId);
    }

    @Override
    public ProjectScoreRank getPersonalTotalRank(Long userId) {
        return projectScoreRankMapper.getTotalRank(userId);
    }
}
