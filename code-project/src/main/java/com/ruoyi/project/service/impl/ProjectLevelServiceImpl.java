package com.ruoyi.project.service.impl;

import java.util.List;
import java.util.Objects;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.mapper.ProjectQuestionsMapper;
import com.zaxxer.hikari.util.PropertyElf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectLevelMapper;
import com.ruoyi.project.domain.ProjectLevel;
import com.ruoyi.project.service.IProjectLevelService;

import javax.annotation.Resource;

/**
 * 关卡Service业务层处理
 *
 * @author xuexin
 * @date 2025-06-14
 */
@Slf4j
@Service
public class ProjectLevelServiceImpl implements IProjectLevelService {
    @Resource
    private ProjectLevelMapper projectLevelMapper;

    @Resource
    private ProjectQuestionsMapper projectQuestionsMapper;

    /**
     * 查询关卡
     *
     * @param id 关卡主键
     * @return 关卡
     */
    @Override
    public ProjectLevel selectProjectLevelById(Long id) {

        ProjectLevel projectLevel = projectLevelMapper.selectProjectLevelById(id);
        boolean atPassLevel = false;
        boolean upPassLevel = false;

        //获取当前登录用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //判断是否为前台用户
        if (Objects.equals(loginUser.getUser().getUserType(), UserConstants.USER_TYPE_FRONTEND)) {
            //查看关卡详情
            ProjectLevel projectLevelParam = projectLevelMapper.selectProjectLevelById(id);
            //计算上一个关卡序号
            long levelCode = 1L;
            if (projectLevelParam.getLevelCode().intValue() > 1 ) {
                levelCode = projectLevelParam.getLevelCode() - 1L;
            }
            projectLevelParam.setLevelCode(levelCode);
            //根据关卡序号和模块ID查询关卡，返回关卡对象
            ProjectLevel template = projectLevelMapper.selectProjectLevelModuleIDAndLevelCode(projectLevelParam);

            //查询上一关是否有通关
            Long upLevelId = projectQuestionsMapper.selectProjectQuestionsByUserIdAndLevelId(template.getModuleId(), loginUser.getUser().getUserId(), template.getId());
            if(ObjectUtils.isNotEmpty(upLevelId) && upLevelId > 0 ){
                upPassLevel = true;
            }

            //当前关卡是否通关
            Long atLevelId = projectQuestionsMapper.selectProjectQuestionsByUserIdAndLevelId(projectLevelParam.getModuleId(), loginUser.getUser().getUserId(), projectLevelParam.getId());
            if(ObjectUtils.isNotEmpty(atLevelId) && atLevelId > 0 ){
                atPassLevel = true;
            }
        }

        projectLevel.setAtPassLevel(atPassLevel);
        projectLevel.setUpPassLevel(upPassLevel);
        return projectLevel;
    }

    /**
     * 查询关卡列表
     *
     * @param projectLevel 关卡
     * @return 关卡
     */
    @Override
    public List<ProjectLevel> selectProjectLevelList(ProjectLevel projectLevel) {
        return projectLevelMapper.selectProjectLevelList(projectLevel);
    }

    /**
     * 新增关卡
     *
     * @param projectLevel 关卡
     * @return 结果
     */
    @Override
    public int insertProjectLevel(ProjectLevel projectLevel) {
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
    public int updateProjectLevel(ProjectLevel projectLevel) {
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
    public int deleteProjectLevelByIds(Long[] ids) {
        return projectLevelMapper.deleteProjectLevelByIds(ids);
    }

    /**
     * 删除关卡信息
     *
     * @param id 关卡主键
     * @return 结果
     */
    @Override
    public int deleteProjectLevelById(Long id) {
        return projectLevelMapper.deleteProjectLevelById(id);
    }
}
