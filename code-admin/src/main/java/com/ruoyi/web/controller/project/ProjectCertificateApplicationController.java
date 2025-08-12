package com.ruoyi.web.controller.project;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project.domain.ProjectCertificateApplication;
import com.ruoyi.project.service.IProjectCertificateApplicationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 证书申请Controller
 * 
 * @author qiuqiuzi
 * @date 2025-08-12
 */
@RestController
@RequestMapping("/project/application")
public class ProjectCertificateApplicationController extends BaseController
{
    @Autowired
    private IProjectCertificateApplicationService projectCertificateApplicationService;

    /**
     * 查询证书申请列表
     */
    @PreAuthorize("@ss.hasPermi('project:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectCertificateApplication projectCertificateApplication)
    {
        startPage();
        List<ProjectCertificateApplication> list = projectCertificateApplicationService.selectProjectCertificateApplicationList(projectCertificateApplication);
        return getDataTable(list);
    }

    /**
     * 导出证书申请列表
     */
    @PreAuthorize("@ss.hasPermi('project:application:export')")
    @Log(title = "证书申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectCertificateApplication projectCertificateApplication)
    {
        List<ProjectCertificateApplication> list = projectCertificateApplicationService.selectProjectCertificateApplicationList(projectCertificateApplication);
        ExcelUtil<ProjectCertificateApplication> util = new ExcelUtil<ProjectCertificateApplication>(ProjectCertificateApplication.class);
        util.exportExcel(response, list, "证书申请数据");
    }

    /**
     * 获取证书申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:application:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectCertificateApplicationService.selectProjectCertificateApplicationById(id));
    }

    /**
     * 新增证书申请
     */
    @PreAuthorize("@ss.hasPermi('project:application:add')")
    @Log(title = "证书申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectCertificateApplication projectCertificateApplication)
    {
        return toAjax(projectCertificateApplicationService.insertProjectCertificateApplication(projectCertificateApplication));
    }

    /**
     * 修改证书申请
     */
    @PreAuthorize("@ss.hasPermi('project:application:edit')")
    @Log(title = "证书申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectCertificateApplication projectCertificateApplication)
    {
        return toAjax(projectCertificateApplicationService.updateProjectCertificateApplication(projectCertificateApplication));
    }

    /**
     * 删除证书申请
     */
    @PreAuthorize("@ss.hasPermi('project:application:remove')")
    @Log(title = "证书申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectCertificateApplicationService.deleteProjectCertificateApplicationByIds(ids));
    }
}
