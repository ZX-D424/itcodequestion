package com.ruoyi.web.controller.project;

import java.util.Date;
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
import com.ruoyi.project.domain.ProjectQuestions;
import com.ruoyi.project.service.IProjectQuestionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 答题评分Controller
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@RestController
@RequestMapping("/project/questions")
public class ProjectQuestionsController extends BaseController
{
    @Resource
    private IProjectQuestionsService projectQuestionsService;

    /**
     * 查询答题评分列表
     */
    @PreAuthorize("@ss.hasPermi('project:questions:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectQuestions projectQuestions)
    {
        startPage();
        List<ProjectQuestions> list = projectQuestionsService.selectProjectQuestionsList(projectQuestions);
        return getDataTable(list);
    }

    /**
     * 导出答题评分列表
     */
    @PreAuthorize("@ss.hasPermi('project:questions:export')")
    @Log(title = "答题评分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectQuestions projectQuestions)
    {
        List<ProjectQuestions> list = projectQuestionsService.selectProjectQuestionsList(projectQuestions);
        ExcelUtil<ProjectQuestions> util = new ExcelUtil<ProjectQuestions>(ProjectQuestions.class);
        util.exportExcel(response, list, "答题评分数据");
    }

    /**
     * 获取答题评分详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:questions:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectQuestionsService.selectProjectQuestionsById(id));
    }

    /**
     * 新增答题评分
     */
   // @PreAuthorize("@ss.hasPermi('project:questions:add')")
    @Log(title = "答题评分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectQuestions projectQuestions)
    {
        projectQuestions.setCreateBy(getUsername());
        projectQuestions.setUserId(getUserId());
        projectQuestions.setSubmitTime(new Date());
        return projectQuestionsService.insertProjectQuestions(projectQuestions);
    }

    /**
     * 修改答题评分
     */
    @PreAuthorize("@ss.hasPermi('project:questions:edit')")
    @Log(title = "答题评分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectQuestions projectQuestions)
    {
        projectQuestions.setCheckUserId(getUserId());
        projectQuestions.setCheckTime(new Date());
        return toAjax(projectQuestionsService.updateProjectQuestions(projectQuestions));
    }

    /**
     * 删除答题评分
     */
    @PreAuthorize("@ss.hasPermi('project:questions:remove')")
    @Log(title = "答题评分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectQuestionsService.deleteProjectQuestionsByIds(ids));
    }
}
