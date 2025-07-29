package com.ruoyi.web.controller.project;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectMenu;
import com.ruoyi.project.service.IProjectMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 项目菜单Controller
 * 
 * @author xuexin
 * @date 2025-06-12
 */
@RestController
@RequestMapping("/project/menu")
public class ProjectMenuController extends BaseController
{
    @Resource
    private IProjectMenuService projectMenuService;

    /**
     * 查询项目菜单列表
     */
    @PreAuthorize("@ss.hasPermi('project:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectMenu projectMenu)
    {
        startPage();
        List<ProjectMenu> list = projectMenuService.selectProjectMenuList(projectMenu);
        return getDataTable(list);
    }

    /**
     * 导出项目菜单列表
     */
    @PreAuthorize("@ss.hasPermi('project:menu:export')")
    @Log(title = "项目菜单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectMenu projectMenu)
    {
        List<ProjectMenu> list = projectMenuService.selectProjectMenuList(projectMenu);
        ExcelUtil<ProjectMenu> util = new ExcelUtil<ProjectMenu>(ProjectMenu.class);
        util.exportExcel(response, list, "项目菜单数据");
    }

    /**
     * 获取项目菜单详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:menu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectMenuService.selectProjectMenuById(id));
    }

    /**
     * 新增项目菜单
     */
    @PreAuthorize("@ss.hasPermi('project:menu:add')")
    @Log(title = "项目菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectMenu projectMenu)
    {
        projectMenu.setCreateTime(new Date());
        projectMenu.setCreateBy(getUsername());
        return toAjax(projectMenuService.insertProjectMenu(projectMenu));
    }

    /**
     * 修改项目菜单
     */
    @PreAuthorize("@ss.hasPermi('project:menu:edit')")
    @Log(title = "项目菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectMenu projectMenu)
    {
        projectMenu.setUpdateBy(getUsername());
        projectMenu.setUpdateTime(new Date());
        return toAjax(projectMenuService.updateProjectMenu(projectMenu));
    }

    /**
     * 删除项目菜单
     */
    @PreAuthorize("@ss.hasPermi('project:menu:remove')")
    @Log(title = "项目菜单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectMenuService.deleteProjectMenuByIds(ids));
    }

    /**
     * 查询项目菜单列表
     */
    @PreAuthorize("@ss.hasPermi('project:menu:list')")
    @GetMapping("/getMenuDataList")
    public AjaxResult getMenuDataList()
    {
        List<ProjectMenu> list = projectMenuService.getMenuDataList();
        return AjaxResult.success(list);
    }

}
