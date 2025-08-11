package com.ruoyi.project.mapper;

import java.time.LocalDate;
import java.util.List;
import com.ruoyi.project.domain.ProjectQuestions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 答题评分Mapper接口
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Mapper
public interface ProjectQuestionsMapper 
{
    /**
     * 查询答题评分
     * 
     * @param id 答题评分主键
     * @return 答题评分
     */
    public ProjectQuestions selectProjectQuestionsById(Long id);

    /**
     * 查询答题评分列表
     * 
     * @param projectQuestions 答题评分
     * @return 答题评分集合
     */
    public List<ProjectQuestions> selectProjectQuestionsList(ProjectQuestions projectQuestions);

    /**
     * 新增答题评分
     * 
     * @param projectQuestions 答题评分
     * @return 结果
     */
    public int insertProjectQuestions(ProjectQuestions projectQuestions);

    /**
     * 修改答题评分
     * 
     * @param projectQuestions 答题评分
     * @return 结果
     */
    public int updateProjectQuestions(ProjectQuestions projectQuestions);

    /**
     * 删除答题评分
     * 
     * @param id 答题评分主键
     * @return 结果
     */
    public int deleteProjectQuestionsById(Long id);

    /**
     * 批量删除答题评分
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectQuestionsByIds(Long[] ids);

    /**
     * 查询答题评分
     *
     * @param userId 答题评分主键
     * @param levelId 关卡Id
     * @return 答题评分
     */
    public Long selectProjectQuestionsByUserIdAndLevelId(@Param("moduleId") Long moduleId,@Param("userId") Long userId,@Param("levelId")  Long levelId);

    List<ProjectQuestions> selectUsersCompletedLastLevel(LocalDate yesterday);

    Double getModuleScoreByUserId(Long userId, Long moduleId);

    Double getUserTotalScore(Long userId);
}
