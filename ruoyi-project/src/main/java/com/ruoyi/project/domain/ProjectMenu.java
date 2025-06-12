package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目菜单对象 project_menu
 * 
 * @author xuexin
 * @date 2025-06-12
 */
@Data
public class ProjectMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 是否禁用，0正常，1禁用 */
    @Excel(name = "是否禁用，0正常，1禁用")
    private Long isDisabled;

    /** 排序值（升序） */
    @Excel(name = "排序值", readConverterExp = "升=序")
    private Long sortNum;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;



}
