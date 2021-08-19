package com.wupao.oneclickrelease.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.vo.permission.RoleVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wuxianglong
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询角色列表
     *
     * @param page     分页
     * @param roleName 角色名称
     * @return 角色列表
     */
    IPage<RoleVO> findRoleList(IPage<?> page, String roleName);

    /**
     * 查询角色信息
     *
     * @param id id
     * @return 角色信息
     */
    RoleVO findRoleInfo(@Param("id") Long id);
}
