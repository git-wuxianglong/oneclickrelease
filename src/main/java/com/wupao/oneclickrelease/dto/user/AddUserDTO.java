package com.wupao.oneclickrelease.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 添加用户
 * <br>创建时间：2021/8/19
 *
 * @author 吴翔龙
 */
@Data
public class AddUserDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String nickname;
    private String email;
    @NotBlank(message = "电话号码不能为空")
    private String phoneNumber;
    private Integer sex;
    @NotBlank(message = "密码不能为空")
    private String password;
}
