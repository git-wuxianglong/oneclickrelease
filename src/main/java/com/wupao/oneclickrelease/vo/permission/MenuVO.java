package com.wupao.oneclickrelease.vo.permission;

import lombok.Data;

import java.util.Date;

/**
 * @author wuxianglong
 */
@Data
public class MenuVO {
    private Long id;
    private Long parentId;
    private String menuName;
    private String url;
    private String icon;
    private Integer type;
    private Long orderNum;
    private Date createTime;
    private Date modifiedTime;
    private Boolean disabled;
    private Integer open;
    private String perms;
}
