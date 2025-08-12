package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProjectCertificateApplication;

/**
 * 证书申请Mapper接口
 * 
 * @author qiuqiuzi
 * @date 2025-08-12
 */
public interface ProjectCertificateApplicationMapper 
{
    /**
     * 查询证书申请
     * 
     * @param id 证书申请主键
     * @return 证书申请
     */
    public ProjectCertificateApplication selectProjectCertificateApplicationById(Long id);

    /**
     * 查询证书申请列表
     * 
     * @param projectCertificateApplication 证书申请
     * @return 证书申请集合
     */
    public List<ProjectCertificateApplication> selectProjectCertificateApplicationList(ProjectCertificateApplication projectCertificateApplication);

    /**
     * 新增证书申请
     * 
     * @param projectCertificateApplication 证书申请
     * @return 结果
     */
    public int insertProjectCertificateApplication(ProjectCertificateApplication projectCertificateApplication);

    /**
     * 修改证书申请
     * 
     * @param projectCertificateApplication 证书申请
     * @return 结果
     */
    public int updateProjectCertificateApplication(ProjectCertificateApplication projectCertificateApplication);

    /**
     * 删除证书申请
     * 
     * @param id 证书申请主键
     * @return 结果
     */
    public int deleteProjectCertificateApplicationById(Long id);

    /**
     * 批量删除证书申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectCertificateApplicationByIds(Long[] ids);
}
