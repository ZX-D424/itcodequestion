package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 答题评分对象 project_questions
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Data
public class ProjectQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模块ID */
    @Excel(name = "模块ID")
    private Long moduleId;

    /** 关卡ID */
    @Excel(name = "关卡ID")
    private Long levelId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 代码截图url */
    @Excel(name = "代码截图url")
    private String imgUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String mark;

    /** 附件url */
    @Excel(name = "附件url")
    private String fileUrl;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 答题开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "答题开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 批改人ID */
    @Excel(name = "批改人ID")
    private Long checkUserId;

    /** 批改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "批改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /** 评分 */
    @Excel(name = "评分")
    private Long score;

    /** 评分备注 */
    @Excel(name = "评分备注")
    private String checkMark;


}
