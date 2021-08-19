package com.wupao.oneclickrelease.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息
 * <br>创建时间：2021/8/18
 *
 * @author 吴翔龙
 */
@Data
public class UserVO {
    private Long id;
    private String userNum;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Boolean status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer sex;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日")
    private Date birth;
}
