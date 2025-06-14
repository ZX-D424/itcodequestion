package com.ruoyi.project.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关卡对象 project_level
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@Data
public class ProjectLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 模块ID */
    @Excel(name = "模块ID")
    private Long moduleId;

    /** 模块ID */
    @Excel(name = "模块名称")
    private String moduleName;

    /** 关卡编码 */
    @Excel(name = "关卡编码")
    private Long levelCode;

    /** 关卡标题 */
    @Excel(name = "关卡标题")
    private String levelTitle;

    /** 关卡信息 */
    @Excel(name = "关卡信息")
    private String levelInfo;

    /** 关卡图片 */
    @Excel(name = "关卡图片")
    private String imgUrl;

}
