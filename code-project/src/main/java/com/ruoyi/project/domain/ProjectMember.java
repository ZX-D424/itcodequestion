package com.ruoyi.project.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目成员对象 project_member
 * 
 * @author xuexin
 * @date 2025-06-19
 */
@Data
public class ProjectMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 个人资料图片 */
    @Excel(name = "个人资料图片")
    private String profilePicture;

    /** 昵称绰号 */
    @Excel(name = "昵称绰号")
    private String nickName;

    /** 成员类型（字典表） */
    @Excel(name = "成员类型", readConverterExp = "字=典表")
    private String memberType;

    /** 擅长方向 */
    @Excel(name = "擅长方向")
    private String proficiencyArea;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 个人空间 */
    @Excel(name = "个人空间")
    private String personalSpace;

    /** gitee地址 */
    @Excel(name = "gitee地址")
    private String giteeLink;

    /** github地址 */
    @Excel(name = "github地址")
    private String githubLink;

    /** 学校名称 */
    @Excel(name = "学校名称")
    private String schoolName;

    /** 专业(字典) */
    @Excel(name = "专业(字典)")
    private String specialty;

    /** 学历（字典） */
    @Excel(name = "学历", readConverterExp = "字=典")
    private String degree;

    /** 审核状态（0通过，1审核中，2审核失败，） */
    @Excel(name = "审核状态", readConverterExp = "0=通过，1审核中，2审核失败，")
    private Long auditStatus;

    /** 简介 */
    @Excel(name = "简介")
    private String contentInfo;

}
