package com.wupao.oneclickrelease.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 菜单
 *
 * @author wuxianglong
 */
@Data
@TableName(value = "tb_menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String menuName;
    private String url;
    private String icon;
    private Integer open;
    private Integer type;
    private Long orderNum;
    private Date createTime;
    private Date modifiedTime;
    private Integer available;
    private String perms;
    private Integer deleted;
    private Long insertUid;
    private Long updateUid;
}
