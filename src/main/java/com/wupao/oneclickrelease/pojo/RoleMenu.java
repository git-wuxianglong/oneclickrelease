package com.wupao.oneclickrelease.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 角色菜单
 *
 * @author wuxianglong
 */
@Data
@TableName(value = "tb_role_menu")
public class RoleMenu {
    private Long roleId;
    private Long menuId;
    private Integer deleted;
    private Long insertUid;
    private Long updateUid;
    private Date createTime;
    private Date modifiedTime;
}
