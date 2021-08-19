package com.wupao.oneclickrelease.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 更新用户
 * <br>创建时间：2021/8/18
 *
 * @author 吴翔龙
 */
@Data
public class UserEditDTO {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String nickname;
    private String email;
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;
    private Integer sex;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日")
    private Date birth;
    private String password;
}
