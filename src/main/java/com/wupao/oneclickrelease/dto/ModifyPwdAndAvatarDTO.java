package com.wupao.oneclickrelease.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改头像和密码
 * <br>创建时间：2021/8/18
 *
 * @author 吴翔龙
 */
@Data
public class ModifyPwdAndAvatarDTO {
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty("头像")
    private String avatar;
}
