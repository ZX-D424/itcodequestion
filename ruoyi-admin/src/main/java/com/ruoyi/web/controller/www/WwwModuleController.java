package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectModule;
import com.ruoyi.project.service.IProjectModuleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目模块Controller
 * 
 * @author xuexin
 * @date 2025-06-12
 */
@RestController
@RequestMapping("/www/module")
public class WwwModuleController extends BaseController
{
    @Resource
    private IProjectModuleService projectModuleService;


    /**
     * 查询项目模块列表
     */
    @GetMapping("/getModuleDataListByMenuId/{menuId}")
    @RateLimiter(count = 200, time = 60)
    public AjaxResult getModuleDataListByMenuId(@PathVariable Long menuId)
    {
        List<ProjectModule> list = projectModuleService.getModuleDataListByMenuId(menuId);
        return AjaxResult.success(list);
    }

}
