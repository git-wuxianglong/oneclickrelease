package com.wupao.oneclickrelease.entity;

import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.pojo.User;
import com.wupao.oneclickrelease.vo.permission.PermissionRuleGroupInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author wuxianglong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {
    /**
     * 当前用户对象
     */
    private User user;
    /**
     * 当前用户具有的角色
     */
    private List<Role> roles;
    /**
     * 当前用户具有的url
     */
    private Set<String> urls;
    /**
     * 包括url+permission
     */
    private List<Menu> menus;
    /**
     * 当前用户具有的权限API:例如[user:add],[user:delete]...
     */
    private Set<String> permissions;
    /**
     * session id
     */
    private String id;
    /**
     * 用户 id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户主机地址
     */
    private String host;
    /**
     * 用户登录时系统 IP
     */
    private String systemHost;
    /**
     * 状态
     */
    private String status;
    /**
     * session 创建时间
     */
    private String startTimestamp;
    /**
     * session 最后访问时间
     */
    private String lastAccessTime;
    /**
     * 超时时间
     */
    private Long timeout;
    /**
     * 所在地
     */
    private String location;
    /**
     * 是否为当前登录用户
     */
    private Boolean current;
    /**
     * 部门id
     */
    private Long departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 直属上级
     */
    private String superiorId;
    /**
     * 下级人员 多个逗号相隔
     */
    private String underlingId;
    /**
     * 数据权限配置
     */
    private List<PermissionRuleGroupInfo> permissionRules;
}
