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
import com.ruoyi.project.domain.ProjectLevel;
import com.ruoyi.project.service.IProjectLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关卡Controller
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/project/level")
public class ProjectLevelController extends BaseController
{
    @Resource
    private IProjectLevelService projectLevelService;

    /**
     * 查询关卡列表
     */
    @PreAuthorize("@ss.hasPermi('project:level:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectLevel projectLevel)
    {
        startPage();
        List<ProjectLevel> list = projectLevelService.selectProjectLevelList(projectLevel);
        return getDataTable(list);
    }

    /**
     * 导出关卡列表
     */
    @PreAuthorize("@ss.hasPermi('project:level:export')")
    @Log(title = "关卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectLevel projectLevel)
    {
        List<ProjectLevel> list = projectLevelService.selectProjectLevelList(projectLevel);
        ExcelUtil<ProjectLevel> util = new ExcelUtil<ProjectLevel>(ProjectLevel.class);
        util.exportExcel(response, list, "关卡数据");
    }

    /**
     * 获取关卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
          return success(projectLevelService.selectProjectLevelById(id));
    }

    /**
     * 新增关卡
     */
    @PreAuthorize("@ss.hasPermi('project:level:add')")
    @Log(title = "关卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectLevel projectLevel)
    {
        projectLevel.setCreateBy(getUsername());
        return toAjax(projectLevelService.insertProjectLevel(projectLevel));
    }

    /**
     * 修改关卡
     */
    @PreAuthorize("@ss.hasPermi('project:level:edit')")
    @Log(title = "关卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectLevel projectLevel)
    {
        projectLevel.setUpdateBy(getUsername());
        return toAjax(projectLevelService.updateProjectLevel(projectLevel));
    }

    /**
     * 删除关卡
     */
    @PreAuthorize("@ss.hasPermi('project:level:remove')")
    @Log(title = "关卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectLevelService.deleteProjectLevelByIds(ids));
    }

    /**
     * 获取关卡详细信息
     */
    @GetMapping(value = "/wwwGetInfo/{id}")
    public AjaxResult wwwGetInfo(@PathVariable("id") Long id)
    {
        return success(projectLevelService.selectProjectLevelById(id));
    }
}
