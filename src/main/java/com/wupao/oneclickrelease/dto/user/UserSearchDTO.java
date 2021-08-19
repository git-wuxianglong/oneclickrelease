package com.wupao.oneclickrelease.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 搜索用户
 * <br>创建时间：2021/8/19
 *
 * @author 吴翔龙
 */
@Data
public class UserSearchDTO {
    private String userNum;
    private String username;
    private String phoneNumber;
    private Integer status;
    private String createStartTime;
    private String createEndTime;
}
