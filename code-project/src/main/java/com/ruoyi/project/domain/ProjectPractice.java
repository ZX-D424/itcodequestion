package com.ruoyi.project.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 练习项目对象 project_practice
 * 
 * @author xuexin
 * @date 2025-06-14
 */
@Data
public class ProjectPractice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模块ID */
    @Excel(name = "模块ID")
    private Long moduleId;

    /** 模块ID */
    @Excel(name = "模块名称")
    private String moduleName;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String content;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imgUrl;

    /** 附件链接【可多个】 */
    @Excel(name = "附件链接【可多个】")
    private String fileUrl;

    /** 排序 */
    @Excel(name = "排序")
    private Integer numSort;

    /** QQ号码 */
    @Excel(name = "QQ号码")
    private Integer qqNumber;
    /** 邮箱 */
    @Excel(name = "邮箱")
    private String emailNumber;
    /** 二维码 */
    @Excel(name = "二维码")
    private String qrCode;

    @Excel(name = "备注")
    private String mark;


}
