package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectQuestions;
import com.ruoyi.project.service.IProjectQuestionsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 答题评分Controller
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@RestController
@RequestMapping("/www/questions")
public class WwwQuestionsController extends BaseController
{
    @Resource
    private IProjectQuestionsService projectQuestionsService;

    /**
     * 查询用户答题评分列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProjectQuestions projectQuestions)
    {
        System.out.println(projectQuestions);
        projectQuestions.setUserId(getLoginUser().getUserId());
        List<ProjectQuestions> list = projectQuestionsService.selectProjectQuestionsList(projectQuestions);
        return getDataTable(list);
    }

}
