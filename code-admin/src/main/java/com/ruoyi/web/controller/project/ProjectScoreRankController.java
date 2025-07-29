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
import com.ruoyi.project.domain.ProjectScoreRank;
import com.ruoyi.project.service.IProjectScoreRankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 得分排名Controller
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@RestController
@RequestMapping("/project/rank")
public class ProjectScoreRankController extends BaseController
{
    @Autowired
    private IProjectScoreRankService projectScoreRankService;

    /**
     * 查询得分排名列表
     */
    @PreAuthorize("@ss.hasPermi('project:rank:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectScoreRank projectScoreRank)
    {
        startPage();
        List<ProjectScoreRank> list = projectScoreRankService.selectProjectScoreRankList(projectScoreRank);
        return getDataTable(list);
    }

    /**
     * 导出得分排名列表
     */
    @PreAuthorize("@ss.hasPermi('project:rank:export')")
    @Log(title = "得分排名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectScoreRank projectScoreRank)
    {
        List<ProjectScoreRank> list = projectScoreRankService.selectProjectScoreRankList(projectScoreRank);
        ExcelUtil<ProjectScoreRank> util = new ExcelUtil<ProjectScoreRank>(ProjectScoreRank.class);
        util.exportExcel(response, list, "得分排名数据");
    }

    /**
     * 获取得分排名详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:rank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectScoreRankService.selectProjectScoreRankById(id));
    }

    /**
     * 新增得分排名
     */
    @PreAuthorize("@ss.hasPermi('project:rank:add')")
    @Log(title = "得分排名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectScoreRank projectScoreRank)
    {
        return toAjax(projectScoreRankService.insertProjectScoreRank(projectScoreRank));
    }

    /**
     * 修改得分排名
     */
    @PreAuthorize("@ss.hasPermi('project:rank:edit')")
    @Log(title = "得分排名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectScoreRank projectScoreRank)
    {
        return toAjax(projectScoreRankService.updateProjectScoreRank(projectScoreRank));
    }

    /**
     * 删除得分排名
     */
    @PreAuthorize("@ss.hasPermi('project:rank:remove')")
    @Log(title = "得分排名", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectScoreRankService.deleteProjectScoreRankByIds(ids));
    }
}
