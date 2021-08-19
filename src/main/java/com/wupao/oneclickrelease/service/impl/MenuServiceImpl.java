package com.wupao.oneclickrelease.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wupao.oneclickrelease.dto.MenuDTO;
import com.wupao.oneclickrelease.entity.ActiveUser;
import com.wupao.oneclickrelease.enums.ResponseStatusEnum;
import com.wupao.oneclickrelease.exception.MyException;
import com.wupao.oneclickrelease.mapper.MenuMapper;
import com.wupao.oneclickrelease.mapper.RoleMapper;
import com.wupao.oneclickrelease.mapper.RoleMenuMapper;
import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.service.MenuConverter;
import com.wupao.oneclickrelease.service.MenuService;
import com.wupao.oneclickrelease.service.MenuTreeBuilder;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;
import com.wupao.oneclickrelease.vo.permission.MenuVO;
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
 * 菜单
 *
 * @author wuxianglong
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final RoleMapper roleMapper;

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<MenuNodeVO> findMenuTree() {
        List<Menu> menus = new LambdaQueryChainWrapper<>(menuMapper).eq(Menu::getDeleted, 0).list();
        List<MenuNodeVO> menuNodeList = MenuConverter.converterToALLMenuNodeVO(menus);
        return MenuTreeBuilder.build(menuNodeList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public Menu add(MenuDTO menuDto) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        menu.setCreateTime(new Date());
        menu.setModifiedTime(new Date());
        menu.setInsertUid(activeUser.getUser().getId());
        menu.setUpdateUid(activeUser.getUser().getId());
        menu.setAvailable(menuDto.getDisabled() ? 0 : 1);
        if (menuMapper.insert(menu) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
        return menu;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void delete(Long id) {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            MyException.display(ResponseStatusEnum.MENU_NOT_EXIST);
        }
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        menu.setDeleted(1);
        menu.setModifiedTime(new Date());
        menu.setUpdateUid(activeUser.getUser().getId());
        if (menuMapper.updateById(menu) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public MenuVO edit(Long id) {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            MyException.display(ResponseStatusEnum.MENU_NOT_EXIST);
        }
        return MenuConverter.converterToMenuVO(menu);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public void update(Long id, MenuVO menuVO) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Menu dbMenu = menuMapper.selectById(id);
        if (dbMenu == null) {
            MyException.display(ResponseStatusEnum.MENU_NOT_EXIST);
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);
        menu.setId(id);
        menu.setAvailable(menuVO.getDisabled() ? 0 : 1);
        menu.setModifiedTime(new Date());
        menu.setUpdateUid(activeUser.getUser().getId());
        if (menuMapper.updateById(menu) <= 0) {
            MyException.display(ResponseStatusEnum.FAILED);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Long> findOpenIds() {
        List<Long> ids = new ArrayList<>();
        List<Menu> menus = new LambdaQueryChainWrapper<>(menuMapper).eq(Menu::getDeleted, 0).list();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (menu.getOpen() == 1) {
                    ids.add(menu.getId());
                }
            }
        }
        return ids;
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {RuntimeException.class, Exception.class})
    @Override
    public List<Menu> findAll() {
        return new LambdaQueryChainWrapper<>(menuMapper).eq(Menu::getDeleted, 0).list();
    }
}
