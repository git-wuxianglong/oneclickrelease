package com.wupao.oneclickrelease.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 添加菜单
 *
 * @author wuxianglong
 */
@Data
public class MenuDTO {
    private Long id;
    @NotNull(message = "父级ID必须")
    private Long parentId;
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    private String url;
    private String icon;
    @NotNull(message = "菜单类型不为空")
    private Integer type;
    @NotNull(message = "排序数不能为空")
    private Long orderNum;
    private Date createTime;
    private Date modifiedTime;
    @NotNull(message = "菜单状态不能为空")
    private Boolean disabled;
    private Integer open;
    private String perms;
}
