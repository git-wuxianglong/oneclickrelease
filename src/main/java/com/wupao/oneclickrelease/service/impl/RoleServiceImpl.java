package com.wupao.oneclickrelease.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wupao.oneclickrelease.dto.RoleDTO;
import com.wupao.oneclickrelease.entity.ActiveUser;
import com.wupao.oneclickrelease.enums.ResponseStatusEnum;
import com.wupao.oneclickrelease.enums.RoleStatusEnum;
import com.wupao.oneclickrelease.exception.MyException;
import com.wupao.oneclickrelease.mapper.MenuMapper;
import com.wupao.oneclickrelease.mapper.RoleMapper;
import com.wupao.oneclickrelease.mapper.RoleMenuMapper;
import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.pojo.RoleMenu;
import com.wupao.oneclickrelease.service.RoleService;
import com.wupao.oneclickrelease.vo.permission.RoleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 角色
 *
 * @author wuxianglong
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final MenuMapper menuMapper;

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public IPage<RoleVO> findRoleList(Integer pageNum, Integer pageSize, String roleName) {
        Page<RoleVO> page = new Page<>(pageNum, pageSize);
        return roleMapper.findRoleList(page, roleName);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void add(RoleDTO roleDto) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        checksRoleNameExists(roleDto.getRoleName());
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setCreateTime(new Date());
        role.setModifiedTime(new Date());
        role.setUpdateUid(activeUser.getUser().getId());
        role.setInsertUid(activeUser.getUser().getId());
        // 默认启用添加的角色
        role.setStatus(RoleStatusEnum.AVAILABLE.getStatusCode());
        roleMapper.insert(role);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void deleteById(Long id) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Role role = roleMapper.selectById(id);
        if (role == null) {
            MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
        }
        role.setDeleted(1);
        role.setUpdateUid(activeUser.getUser().getId());
        role.setModifiedTime(new Date());
        roleMapper.updateById(role);
        // 删除对应的[角色-菜单]记录
        List<RoleMenu> list = new LambdaQueryChainWrapper<>(roleMenuMapper)
                .eq(RoleMenu::getRoleId, id)
                .eq(RoleMenu::getDeleted, 0)
                .list();
        for (RoleMenu roleMenu : list) {
            roleMenu.setDeleted(1);
            roleMenu.setUpdateUid(activeUser.getUser().getId());
            roleMenu.setModifiedTime(new Date());
            roleMenuMapper.updateById(roleMenu);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public RoleVO findRoleInfo(Long id) {
        return roleMapper.findRoleInfo(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void update(Long id, RoleDTO roleDto) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Role dbRole = roleMapper.selectById(id);
        if (dbRole == null) {
            MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
        }
        checksRoleNameExists(roleDto.getRoleName());
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setId(id);
        role.setModifiedTime(new Date());
        role.setUpdateUid(activeUser.getUser().getId());
        if (roleMapper.updateById(role) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    void checksRoleNameExists(String roleName) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", roleName);
        int count = roleMapper.selectCount(queryWrapper);
        if (count > 0) {
            MyException.display(ResponseStatusEnum.MENU_NAME_EXIST);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void updateStatus(Long id, Boolean status) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Role role = roleMapper.selectById(id);
        if (role == null) {
            MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
        }
        Role t = new Role();
        t.setId(id);
        t.setUpdateUid(activeUser.getUser().getId());
        t.setModifiedTime(new Date());
        t.setStatus(status ? RoleStatusEnum.DISABLE.getStatusCode() : RoleStatusEnum.AVAILABLE.getStatusCode());
        roleMapper.updateById(t);
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Role> findAll() {
        return new LambdaQueryChainWrapper<>(roleMapper).eq(Role::getDeleted, 0).list();
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Long> findMenuIdsByRoleId(Long id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
        }
        List<Long> ids = new ArrayList<>();
        List<RoleMenu> roleMenus = new LambdaQueryChainWrapper<>(roleMenuMapper).eq(RoleMenu::getRoleId, id).eq(RoleMenu::getDeleted, 0).list();
        if (!CollectionUtils.isEmpty(roleMenus)) {
            for (RoleMenu roleMenu : roleMenus) {
                ids.add(roleMenu.getMenuId());
            }
        }
        return ids;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void authority(Long id, Long[] mids) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            MyException.display(ResponseStatusEnum.ROLE_NOT_EXIST);
        }
        UpdateWrapper<RoleMenu> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("role_id", id);
        deleteWrapper.eq("deleted", 0);
        if (roleMenuMapper.delete(deleteWrapper) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
        // 增加现在分配的角色
        if (mids.length > 0) {
            for (Long mid : mids) {
                Menu menu = menuMapper.selectById(mid);
                if (menu == null) {
                    MyException.display(ResponseStatusEnum.MENU_NOT_EXIST);
                }
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(id);
                roleMenu.setMenuId(mid);
                roleMenuMapper.insert(roleMenu);
            }
        }
    }

}
