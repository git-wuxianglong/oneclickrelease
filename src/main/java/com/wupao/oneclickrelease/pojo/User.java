package com.wupao.oneclickrelease.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User
 * <br>创建时间：2021/4/6
 *
 * @author 吴翔龙
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userNum;
    private String username;
    private String nickname;
    private String salt;
    private String password;
    private String email;
    private String avatar;
    private String phoneNumber;
    private Integer status;
    private Integer sex;
    private Integer type;
    private Date birthday;
    private Integer deleted;
    private Long insertUid;
    private Long updateUid;
    private Date createTime;
    private Date updateTime;
    private String version;
}
