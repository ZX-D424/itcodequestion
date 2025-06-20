package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.project.domain.ProjectMember;
import com.ruoyi.project.service.IProjectMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目成员Controller
 * 
 * @author xuexin
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/www/member")
public class WwwMemberController extends BaseController
{
    @Resource
    private IProjectMemberService projectMemberService;

    /**
     * 查询项目成员列表
     */
    @GetMapping("/getMemberDataList")
    @RateLimiter(count = 200, time = 1)
    public AjaxResult getMemberDataList(){
        ProjectMember projectMember = new ProjectMember();
        List<ProjectMember> list = projectMemberService.selectProjectMemberList(projectMember);
        return AjaxResult.success(list);
    }


}
