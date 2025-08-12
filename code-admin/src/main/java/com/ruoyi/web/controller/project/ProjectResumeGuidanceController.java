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
import com.ruoyi.project.domain.ProjectResumeGuidance;
import com.ruoyi.project.service.IProjectResumeGuidanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 简历指导记录Controller
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
@RestController
@RequestMapping("/project/guidance")
public class ProjectResumeGuidanceController extends BaseController
{
    @Autowired
    private IProjectResumeGuidanceService projectResumeGuidanceService;

    /**
     * 查询简历指导记录列表
     */
    @PreAuthorize("@ss.hasPermi('project:guidance:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectResumeGuidance projectResumeGuidance)
    {
        startPage();
        List<ProjectResumeGuidance> list = projectResumeGuidanceService.selectProjectResumeGuidanceList(projectResumeGuidance);
        return getDataTable(list);
    }

    /**
     * 导出简历指导记录列表
     */
    @PreAuthorize("@ss.hasPermi('project:guidance:export')")
    @Log(title = "简历指导记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectResumeGuidance projectResumeGuidance)
    {
        List<ProjectResumeGuidance> list = projectResumeGuidanceService.selectProjectResumeGuidanceList(projectResumeGuidance);
        ExcelUtil<ProjectResumeGuidance> util = new ExcelUtil<ProjectResumeGuidance>(ProjectResumeGuidance.class);
        util.exportExcel(response, list, "简历指导记录数据");
    }

    /**
     * 获取简历指导记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:guidance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectResumeGuidanceService.selectProjectResumeGuidanceById(id));
    }

    /**
     * 新增简历指导记录
     */
    @PreAuthorize("@ss.hasPermi('project:guidance:add')")
    @Log(title = "简历指导记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectResumeGuidance projectResumeGuidance)
    {
        return toAjax(projectResumeGuidanceService.insertProjectResumeGuidance(projectResumeGuidance));
    }

    /**
     * 修改简历指导记录
     */
    @PreAuthorize("@ss.hasPermi('project:guidance:edit')")
    @Log(title = "简历指导记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectResumeGuidance projectResumeGuidance)
    {
        return toAjax(projectResumeGuidanceService.updateProjectResumeGuidance(projectResumeGuidance));
    }

    /**
     * 删除简历指导记录
     */
    @PreAuthorize("@ss.hasPermi('project:guidance:remove')")
    @Log(title = "简历指导记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectResumeGuidanceService.deleteProjectResumeGuidanceByIds(ids));
    }
}
