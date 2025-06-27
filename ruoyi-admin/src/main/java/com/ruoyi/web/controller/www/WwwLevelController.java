package com.ruoyi.web.controller.www;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.project.domain.ProjectLevel;
import com.ruoyi.project.service.IProjectLevelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 关卡Controller
 *
 * @author xuexin
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/www/level")
public class WwwLevelController extends BaseController {
    @Resource
    private IProjectLevelService projectLevelService;

    /**
     * 查询关卡列表
     */
    @GetMapping("/getLevelDataList")
    public AjaxResult getLevelDataList(ProjectLevel projectLevel) {
        List<ProjectLevel> list = projectLevelService.selectProjectLevelList(projectLevel);
        return AjaxResult.success(list);
    }
    /**
     * 获取关卡详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectLevelService.selectProjectLevelById(id));
    }

}
