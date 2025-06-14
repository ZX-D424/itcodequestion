package com.ruoyi.web.controller.project;

import java.util.List;
import javax.annotation.Resource;
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
import com.ruoyi.project.domain.ProjectPractice;
import com.ruoyi.project.service.IProjectPracticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 练习项目Controller
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/project/practice")
public class ProjectPracticeController extends BaseController
{
    @Resource
    private IProjectPracticeService projectPracticeService;

    /**
     * 查询练习项目列表
     */
    @PreAuthorize("@ss.hasPermi('project:practice:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectPractice projectPractice)
    {
        startPage();
        List<ProjectPractice> list = projectPracticeService.selectProjectPracticeList(projectPractice);
        return getDataTable(list);
    }

    /**
     * 导出练习项目列表
     */
    @PreAuthorize("@ss.hasPermi('project:practice:export')")
    @Log(title = "练习项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectPractice projectPractice)
    {
        List<ProjectPractice> list = projectPracticeService.selectProjectPracticeList(projectPractice);
        ExcelUtil<ProjectPractice> util = new ExcelUtil<ProjectPractice>(ProjectPractice.class);
        util.exportExcel(response, list, "练习项目数据");
    }

    /**
     * 获取练习项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:practice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectPracticeService.selectProjectPracticeById(id));
    }

    /**
     * 新增练习项目
     */
    @PreAuthorize("@ss.hasPermi('project:practice:add')")
    @Log(title = "练习项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectPractice projectPractice)
    {
        return toAjax(projectPracticeService.insertProjectPractice(projectPractice));
    }

    /**
     * 修改练习项目
     */
    @PreAuthorize("@ss.hasPermi('project:practice:edit')")
    @Log(title = "练习项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectPractice projectPractice)
    {
        return toAjax(projectPracticeService.updateProjectPractice(projectPractice));
    }

    /**
     * 删除练习项目
     */
    @PreAuthorize("@ss.hasPermi('project:practice:remove')")
    @Log(title = "练习项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectPracticeService.deleteProjectPracticeByIds(ids));
    }
}
