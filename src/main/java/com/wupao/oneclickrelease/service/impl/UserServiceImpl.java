package com.wupao.oneclickrelease.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wupao.oneclickrelease.dto.ModifyPwdAndAvatarDTO;
import com.wupao.oneclickrelease.dto.user.AddUserDTO;
import com.wupao.oneclickrelease.dto.user.UserEditDTO;
import com.wupao.oneclickrelease.dto.user.UserSearchDTO;
import com.wupao.oneclickrelease.entity.ActiveUser;
import com.wupao.oneclickrelease.enums.ResponseStatusEnum;
import com.wupao.oneclickrelease.enums.UserStatusEnum;
import com.wupao.oneclickrelease.enums.UserTypeEnum;
import com.wupao.oneclickrelease.exception.MyCustomException;
import com.wupao.oneclickrelease.exception.MyException;
import com.wupao.oneclickrelease.mapper.*;
import com.wupao.oneclickrelease.pojo.*;
import com.wupao.oneclickrelease.service.MenuConverter;
import com.wupao.oneclickrelease.service.MenuTreeBuilder;
import com.wupao.oneclickrelease.service.UserService;
import com.wupao.oneclickrelease.shiro.JwtToken;
import com.wupao.oneclickrelease.utils.JwtUtils;
import com.wupao.oneclickrelease.utils.Md5Utils;
import com.wupao.oneclickrelease.vo.UserInfoVO;
import com.wupao.oneclickrelease.vo.UserVO;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuxianglong
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final MenuMapper menuMapper;

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public User findUserByMobile(String mobile) {
        return userMapper.findUserByMobile(mobile);
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Role> findRolesById(Long id) {
        User dbUser = userMapper.selectById(id);
        if (dbUser == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        List<Role> roles = new ArrayList<>();
        List<UserRole> userRoleList = new LambdaQueryChainWrapper<>(userRoleMapper).eq(UserRole::getUserId, dbUser.getId()).list();
        List<Long> rids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoleList)) {
            for (UserRole userRole : userRoleList) {
                rids.add(userRole.getRoleId());
            }
            if (!CollectionUtils.isEmpty(rids)) {
                for (Long rid : rids) {
                    Role role = roleMapper.selectById(rid);
                    if (role != null) {
                        roles.add(role);
                    }
                }
            }
        }
        return roles;
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Menu> findMenuByRoles(List<Role> roles) {
        List<Menu> menus = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            // 存放用户的菜单id
            Set<Long> menuIds = new HashSet<>();
            List<RoleMenu> roleMenus;
            for (Role role : roles) {
                // 根据角色ID查询权限ID
                roleMenus = new LambdaQueryChainWrapper<>(roleMenuMapper)
                        .eq(RoleMenu::getRoleId, role.getId())
                        .eq(RoleMenu::getDeleted, 0)
                        .list();
                if (!CollectionUtils.isEmpty(roleMenus)) {
                    for (RoleMenu roleMenu : roleMenus) {
                        menuIds.add(roleMenu.getMenuId());
                    }
                }
            }
            if (!CollectionUtils.isEmpty(menuIds)) {
                for (Long menuId : menuIds) {
                    // 该用户所有的菜单
                    Menu menu = menuMapper.selectById(menuId);
                    if (menu != null) {
                        menus.add(menu);
                    }
                }
            }
        }
        return menus;
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<MenuNodeVO> findMenu() {
        List<Menu> menus = new ArrayList<>();
        List<MenuNodeVO> menuNodeList = new ArrayList<>();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (activeUser.getUser().getType() == UserTypeEnum.SYSTEM_ADMIN.getTypeCode()) {
            // 超级管理员
            Menu menu = new Menu();
            menu.setDeleted(0);
            menus = new LambdaQueryChainWrapper<>(menuMapper).eq(Menu::getDeleted, 0).list();
        } else if (activeUser.getUser().getType() == UserTypeEnum.SYSTEM_USER.getTypeCode()) {
            // 普通系统用户
            menus = activeUser.getMenus();
        }
        if (!CollectionUtils.isEmpty(menus)) {
            menuNodeList = MenuConverter.converterToMenuNodeVO(menus);
        }
        // 构建树形菜单
        return MenuTreeBuilder.build(menuNodeList);
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public IPage<UserVO> findUserList(Integer pageNum, Integer pageSize, UserSearchDTO userSearch) {
        Page<UserVO> page = new Page<>(pageNum, pageSize);
        return userMapper.findUserList(page, userSearch);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void deleteById(Long id) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User user = userMapper.selectById(id);
        if (user == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        if (user.getId().equals(activeUser.getUser().getId())) {
            MyException.display(ResponseStatusEnum.CAN_NOT_DELETE_YOURSELF);
        }
        user.setDeleted(1);
        int i = userMapper.updateById(user);
        if (i <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
        UpdateWrapper<UserRole> delete = new UpdateWrapper<>();
        delete.eq("user_id", id);
        int deleteResult = userRoleMapper.delete(delete);
        if (deleteResult <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void updateStatus(Long id, Boolean status) {
        User dbUser = userMapper.selectById(id);
        if (dbUser == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (dbUser.getId().equals(activeUser.getUser().getId())) {
            MyException.display(ResponseStatusEnum.CAN_NOT_DISABLE_YOURSELF);
        }
        User t = new User();
        t.setId(id);
        t.setUpdateUid(activeUser.getUser().getId());
        t.setUpdateTime(new Date());
        t.setStatus(status ? UserStatusEnum.DISABLE.getStatusCode() : UserStatusEnum.AVAILABLE.getStatusCode());
        if (userMapper.updateById(t) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void add(AddUserDTO addUser) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User userByMobile = userMapper.findUserByMobile(addUser.getPhoneNumber());
        if (userByMobile != null) {
            MyException.display(ResponseStatusEnum.MOBILE_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(addUser, user);
        user.setUserNum(System.currentTimeMillis() + "");
        String salt = UUID.randomUUID().toString().substring(0, 32);
        user.setPassword(Md5Utils.md5Encryption(user.getPassword(), salt));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setSalt(salt);
        // 添加的用户默认是普通用户
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());
        // 添加的用户默认启用
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());
        user.setDeleted(0);
        user.setInsertUid(activeUser.getUser().getId());
        user.setUpdateUid(activeUser.getUser().getId());
        user.setVersion(System.currentTimeMillis() + "");
        int insert = userMapper.insert(user);
        log.info("===【UserServiceImpl -> add】===添加用户，操作结果:{}", insert);
        if (insert <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void update(UserEditDTO userEdit) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User dbUser = userMapper.selectById(userEdit.getId());
        if (dbUser == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        User userByMobile = userMapper.findUserByMobile(userEdit.getPhoneNumber());
        if (userByMobile != null) {
            MyException.display(ResponseStatusEnum.MOBILE_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(userEdit, user);
        if (StringUtils.isNotBlank(userEdit.getPassword())) {
            user.setPassword(Md5Utils.md5Encryption(userEdit.getPassword(), dbUser.getSalt()));
        }
        user.setUpdateTime(new Date());
        user.setId(dbUser.getId());
        user.setUpdateUid(activeUser.getUser().getId());
        if (userMapper.updateById(user) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Long> roles(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        List<UserRole> userRoleList = new LambdaQueryChainWrapper<>(userRoleMapper)
                .eq(UserRole::getUserId, user.getId())
                .eq(UserRole::getDeleted, 0)
                .list();
        List<Long> roleIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoleList)) {
            for (UserRole userRole : userRoleList) {
                Role role = roleMapper.selectById(userRole.getRoleId());
                if (role != null) {
                    roleIds.add(role.getId());
                }
            }
        }
        return roleIds;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    public void assignRoles(Long id, Long[] rids) {
        User user = userMapper.selectById(id);
        if (user == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        // 删除之前分配的
        UpdateWrapper<UserRole> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("user_id", user.getId());
        if (userRoleMapper.delete(deleteWrapper) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
        // 增加现在分配的
        if (rids.length > 0) {
            for (Long rid : rids) {
                Role role = roleMapper.selectById(rid);
                if (role == null) {
                    MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
                }
                // 判断角色状态
                if (role.getStatus() == 0) {
                    MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
                }
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(rid);
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public String login(String mobile, String password) {
        String token;
        User user = findUserByMobile(mobile);
        if (user == null) {
            MyException.display(ResponseStatusEnum.ACCOUNT_NOT_EXIST);
        }
        String salt = user.getSalt();
        // 秘钥为盐
        String target = Md5Utils.md5Encryption(password, salt);
        // 生成Token
        token = JwtUtils.sign(mobile, target);
        JwtToken jwtToken = new JwtToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            MyException.display(ResponseStatusEnum.LOGIN_FAIL);
        }
        return token;
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public UserInfoVO info() {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAvatar(activeUser.getUser().getAvatar());
        userInfoVO.setUsername(activeUser.getUser().getUsername());
        userInfoVO.setUrl(activeUser.getUrls());
        userInfoVO.setNickname(activeUser.getUser().getNickname());
        List<String> roleNames = activeUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        userInfoVO.setRoles(roleNames);
        userInfoVO.setPerms(activeUser.getPermissions());
        userInfoVO.setIsAdmin(activeUser.getUser().getType() == UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
        return userInfoVO;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void modifyUserAvatar(@NotNull(message = "头像不能为空") String avatar) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User user = activeUser.getUser();
        User u = new User();
        u.setId(user.getId());
        u.setUpdateTime(new Date());
        u.setUpdateUid(user.getId());
        u.setAvatar(avatar);
        userMapper.updateById(u);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void modifyAvatarAndPwd(ModifyPwdAndAvatarDTO modifyPwdAndAvatar) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isBlank(modifyPwdAndAvatar.getAvatar()) && StringUtils.isBlank(modifyPwdAndAvatar.getPassword())) {
            return;
        }
        User user = activeUser.getUser();
        User u = new User();
        u.setId(user.getId());
        if (StringUtils.isNotBlank(modifyPwdAndAvatar.getAvatar())) {
            u.setAvatar(modifyPwdAndAvatar.getAvatar());
        }
        if (StringUtils.isNotBlank(modifyPwdAndAvatar.getPassword())) {
            u.setPassword(Md5Utils.md5Encryption(modifyPwdAndAvatar.getPassword(), user.getSalt()));
        }
        u.setUpdateUid(activeUser.getUser().getId());
        u.setUpdateTime(new Date());
        int i = userMapper.updateById(u);
        if (i <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Override
    public UserVO findUserById(Long userId) {
        UserVO userVO = new UserVO();
        User user = userMapper.selectById(userId);
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
