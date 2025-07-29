package com.ruoyi.project.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目模块对象 project_module
 * 
 * @author xuexin
 * @date 2025-06-12
 */
@Data
public class ProjectModule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 菜单ID **/
    private Long menuId;

    /** 模块名称 */
    @Excel(name = "模块名称")
    private String name;

    /** 禁用状态（0正常，1禁用） */
    @Excel(name = "禁用状态", readConverterExp = "0=正常，1禁用")
    private Long isDisabled;

    /** 排序值（升序） */
    @Excel(name = "排序值", readConverterExp = "升=序")
    private Long sortNum;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 图片链接 */
    @Excel(name = "图片链接")
    private String imgUrl;

    //菜单名称
    private String menuName;


    //模块类型
    private int type;


}
