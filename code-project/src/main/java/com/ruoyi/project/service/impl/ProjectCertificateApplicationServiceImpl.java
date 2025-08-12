package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectCertificateApplicationMapper;
import com.ruoyi.project.domain.ProjectCertificateApplication;
import com.ruoyi.project.service.IProjectCertificateApplicationService;

/**
 * 证书申请Service业务层处理
 * 
 * @author qiuqiuzi
 * @date 2025-08-12
 */
@Service
public class ProjectCertificateApplicationServiceImpl implements IProjectCertificateApplicationService 
{
    @Autowired
    private ProjectCertificateApplicationMapper projectCertificateApplicationMapper;

    /**
     * 查询证书申请
     * 
     * @param id 证书申请主键
     * @return 证书申请
     */
    @Override
    public ProjectCertificateApplication selectProjectCertificateApplicationById(Long id)
    {
        return projectCertificateApplicationMapper.selectProjectCertificateApplicationById(id);
    }

    /**
     * 查询证书申请列表
     * 
     * @param projectCertificateApplication 证书申请
     * @return 证书申请
     */
    @Override
    public List<ProjectCertificateApplication> selectProjectCertificateApplicationList(ProjectCertificateApplication projectCertificateApplication)
    {
        return projectCertificateApplicationMapper.selectProjectCertificateApplicationList(projectCertificateApplication);
    }

    /**
     * 新增证书申请
     * 
     * @param projectCertificateApplication 证书申请
     * @return 结果
     */
    @Override
    public int insertProjectCertificateApplication(ProjectCertificateApplication projectCertificateApplication)
    {
        projectCertificateApplication.setCreateTime(DateUtils.getNowDate());
        return projectCertificateApplicationMapper.insertProjectCertificateApplication(projectCertificateApplication);
    }

    /**
     * 修改证书申请
     * 
     * @param projectCertificateApplication 证书申请
     * @return 结果
     */
    @Override
    public int updateProjectCertificateApplication(ProjectCertificateApplication projectCertificateApplication)
    {
        projectCertificateApplication.setUpdateTime(DateUtils.getNowDate());
        return projectCertificateApplicationMapper.updateProjectCertificateApplication(projectCertificateApplication);
    }

    /**
     * 批量删除证书申请
     * 
     * @param ids 需要删除的证书申请主键
     * @return 结果
     */
    @Override
    public int deleteProjectCertificateApplicationByIds(Long[] ids)
    {
        return projectCertificateApplicationMapper.deleteProjectCertificateApplicationByIds(ids);
    }

    /**
     * 删除证书申请信息
     * 
     * @param id 证书申请主键
     * @return 结果
     */
    @Override
    public int deleteProjectCertificateApplicationById(Long id)
    {
        return projectCertificateApplicationMapper.deleteProjectCertificateApplicationById(id);
    }
}
