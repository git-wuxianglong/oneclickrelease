package com.wupao.oneclickrelease.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 编辑角色
 *
 * @author wuxianglong
 */
@Data
public class RoleDTO {
    private Long id;
    @NotBlank(message = "角色名必填")
    private String roleName;
    @NotBlank(message = "角色描述信息必填")
    private String remark;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    private Boolean status;
    private Integer permRuleType;
}
