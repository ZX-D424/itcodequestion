package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectMenu;
import com.ruoyi.project.service.IProjectMenuService;
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
@RequestMapping("/www/menu")
public class WwwMenuController extends BaseController
{
    @Resource
    private IProjectMenuService projectMenuService;

    /**
     * 查询项目菜单列表
     */
    @GetMapping("/getMenuDataList")
    @RateLimiter(count = 200, time = 1)
    public AjaxResult getMenuDataList()
    {
        List<ProjectMenu> list = projectMenuService.getMenuDataList();
        return AjaxResult.success(list);
    }

}
