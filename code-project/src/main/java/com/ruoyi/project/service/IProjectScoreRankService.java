package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectScoreRank;

/**
 * 得分排名Service接口
 * 
 * @author xuexin
 * @date 2025-06-28
 */
public interface IProjectScoreRankService 
{
    /**
     * 查询得分排名
     * 
     * @param id 得分排名主键
     * @return 得分排名
     */
    public ProjectScoreRank selectProjectScoreRankById(Long id);

    /**
     * 查询得分排名列表
     * 
     * @param projectScoreRank 得分排名
     * @return 得分排名集合
     */
    public List<ProjectScoreRank> selectProjectScoreRankList(ProjectScoreRank projectScoreRank);

    /**
     * 新增得分排名
     * 
     * @param projectScoreRank 得分排名
     * @return 结果
     */
    public int insertProjectScoreRank(ProjectScoreRank projectScoreRank);

    /**
     * 修改得分排名
     * 
     * @param projectScoreRank 得分排名
     * @return 结果
     */
    public int updateProjectScoreRank(ProjectScoreRank projectScoreRank);

    /**
     * 批量删除得分排名
     * 
     * @param ids 需要删除的得分排名主键集合
     * @return 结果
     */
    public int deleteProjectScoreRankByIds(Long[] ids);

    /**
     * 删除得分排名信息
     * 
     * @param id 得分排名主键
     * @return 结果
     */
    public int deleteProjectScoreRankById(Long id);

    List<ProjectScoreRank> selectProjectModuleRankList(Long moduleId);

    List<ProjectScoreRank> selectProjectTotalRankList();

    ProjectScoreRank getPersonalRank(Long userId,Long moduleId);

    ProjectScoreRank getPersonalTotalRank(Long userId);
}
