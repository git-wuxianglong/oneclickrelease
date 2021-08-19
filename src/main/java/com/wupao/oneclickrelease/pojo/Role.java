package com.wupao.oneclickrelease.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 角色
 *
 * @author wuxianglong
 */
@Data
@TableName(value = "tb_role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String roleName;
    private String remark;
    private Integer status;
    private Integer permRuleType;
    private Integer deleted;
    private Long insertUid;
    private Long updateUid;
    private Date createTime;
    private Date modifiedTime;
}
