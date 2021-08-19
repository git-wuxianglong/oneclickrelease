package com.wupao.oneclickrelease.vo.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 角色
 *
 * @author wuxianglong
 */
@Data
public class RoleVO {
    private Long id;
    private String roleName;
    private String remark;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    private Boolean status;
    private Integer permRuleType;
}
