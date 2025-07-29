package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectPractice;
import com.ruoyi.project.service.IProjectPracticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 练习项目Controller
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/www/practice")
public class WwwPracticeController extends BaseController
{
    @Resource
    private IProjectPracticeService projectPracticeService;

    /**
     * 查询练习项目列表
     */
    @GetMapping("/getPracticeDataList")
    public AjaxResult getPracticeDataList(ProjectPractice projectPractice)
    {
        List<ProjectPractice> list = projectPracticeService.selectProjectPracticeList(projectPractice);
        return AjaxResult.success(list);
    }

}
