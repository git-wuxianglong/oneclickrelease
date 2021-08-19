package com.wupao.oneclickrelease.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;


/**
 * 用户信息
 *
 * @author wuxianglong
 */
@Data
public class UserInfoVO {
    private String username;
    private String nickname;
    private String avatar;
    private Set<String> url;
    private Set<String> perms;
    private List<String> roles;
    private String department;
    private Boolean isAdmin;
}
