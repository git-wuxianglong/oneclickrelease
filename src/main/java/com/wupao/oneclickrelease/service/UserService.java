package com.wupao.oneclickrelease.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wupao.oneclickrelease.dto.ModifyPwdAndAvatarDTO;
import com.wupao.oneclickrelease.dto.user.AddUserDTO;
import com.wupao.oneclickrelease.dto.user.UserEditDTO;
import com.wupao.oneclickrelease.dto.user.UserSearchDTO;
import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.pojo.User;
import com.wupao.oneclickrelease.vo.UserInfoVO;
import com.wupao.oneclickrelease.vo.UserVO;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;

import java.util.List;

/**
 * user
 *
 * @author wuxianglong
 */
public interface UserService {

    /**
     * 根据手机号查询用户
     *
     * @param mobile 用户名
     * @return 用户信息
     */
    User findUserByMobile(String mobile);

    /**
     * 查询用户角色
     *
     * @param id 用户id
     * @return 用户角色 list
     */
    List<Role> findRolesById(Long id);

    /**
     * 根据用户角色查询用户的菜单
     * 菜单: menu+button
     *
     * @param roles 用户的角色
     * @return 用户菜单list
     */
    List<Menu> findMenuByRoles(List<Role> roles);

    /**
     * 加载菜单
     *
     * @return 菜单list
     */
    List<MenuNodeVO> findMenu();

    /**
     * 分页查询用户列表
     *
     * @param pageNum    分页
     * @param pageSize   分页
     * @param userSearch 搜索信息
     * @return 用户列表
     */
    IPage<UserVO> findUserList(Integer pageNum, Integer pageSize, UserSearchDTO userSearch);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    void deleteById(Long id);

    /**
     * 更新状态
     *
     * @param id     用户id
     * @param status 状态
     */
    void updateStatus(Long id, Boolean status);

    /**
     * 添加用户
     *
     * @param addUser 添加用户
     */
    void add(AddUserDTO addUser);

    /**
     * 更新用户信息
     *
     * @param userEdit 用户信息
     */
    void update(UserEditDTO userEdit);

    /**
     * 已拥有的角色ids
     *
     * @param id 用户id
     * @return 用户角色 id list
     */
    List<Long> roles(Long id);

    /**
     * 分配角色
     *
     * @param id   用户id
     * @param rids 角色id list
     */
    void assignRoles(Long id, Long[] rids);

    /**
     * 用户登入
     *
     * @param mobile   手机号
     * @param password 密码
     * @return token
     */
    String login(String mobile, String password);

    /**
     * 用户详情
     *
     * @return 用户详情
     */
    UserInfoVO info();

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    void modifyUserAvatar(String avatar);

    /**
     * 修改头像和密码
     *
     * @param modifyPwdAndAvatar 头像和密码
     */
    void modifyAvatarAndPwd(ModifyPwdAndAvatarDTO modifyPwdAndAvatar);

    /**
     * 根据id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserVO findUserById(Long userId);
}
