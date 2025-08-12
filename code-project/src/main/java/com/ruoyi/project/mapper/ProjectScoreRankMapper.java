package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProjectScoreRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 得分排名Mapper接口
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Mapper
public interface ProjectScoreRankMapper 
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
     * 删除得分排名
     * 
     * @param id 得分排名主键
     * @return 结果
     */
    public int deleteProjectScoreRankById(Long id);

    /**
     * 批量删除得分排名
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectScoreRankByIds(Long[] ids);

    List<ProjectScoreRank> selectProjectModuleRankList(Long moduleId);

    List<ProjectScoreRank> selectProjectTotalRankList();

    ProjectScoreRank selectModuleScoreByUserId(@Param("userId") Long userId, @Param("moduleId") Long moduleId);

    Long getRank(@Param("userId") Long userId, @Param("moduleId") Long moduleId);
}
