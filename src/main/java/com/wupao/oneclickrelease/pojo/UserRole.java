package com.wupao.oneclickrelease.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户角色
 *
 * @author wuxianglong
 */
@Data
@TableName(value = "tb_user_role")
public class UserRole {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long roleId;
    private Integer deleted;
    private Long insertUid;
    private Long updateUid;
    private Date createTime;
    private Date modifiedTime;
}
