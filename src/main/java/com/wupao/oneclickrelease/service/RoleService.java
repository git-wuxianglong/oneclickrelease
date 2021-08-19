package com.wupao.oneclickrelease.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wupao.oneclickrelease.dto.RoleDTO;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.vo.permission.RoleVO;

import java.util.List;

/**
 * 角色
 *
 * @author wuxianglong
 */
public interface RoleService {

    /**
     * 分页查询角色列表
     *
     * @param pageNum  分页
     * @param pageSize 分页
     * @param roleName 角色名称
     * @return 角色列表
     */
    IPage<RoleVO> findRoleList(Integer pageNum, Integer pageSize, String roleName);

    /**
     * 添加角色
     *
     * @param role 角色信息
     */
    void add(RoleDTO role);

    /**
     * 删除角色
     *
     * @param id 角色id
     */
    void deleteById(Long id);

    /**
     * 查询角色信息
     *
     * @param id 角色id
     * @return 角色信息
     */
    RoleVO findRoleInfo(Long id);

    /**
     * 更新角色
     *
     * @param id      角色id
     * @param roleDto 更新角色信息
     */
    void update(Long id, RoleDTO roleDto);

    /**
     * 更新角色状态
     *
     * @param id     角色id
     * @param status 状态
     */
    void updateStatus(Long id, Boolean status);

    /**
     * 查询所有的角色
     *
     * @return 所有的角色
     */
    List<Role> findAll();

    /**
     * 查询角色拥有的菜单权限id
     *
     * @param id 角色id
     * @return 菜单权限id list
     */
    List<Long> findMenuIdsByRoleId(Long id);

    /**
     * 角色授权
     *
     * @param id      角色id
     * @param midList 权限 list
     */
    void authority(Long id, Long[] midList);
}
