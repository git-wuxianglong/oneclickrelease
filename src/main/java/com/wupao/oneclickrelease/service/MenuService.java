package com.wupao.oneclickrelease.service;


import com.wupao.oneclickrelease.dto.MenuDTO;
import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;
import com.wupao.oneclickrelease.vo.permission.MenuVO;

import java.util.List;

/**
 * @author wuxianglong
 */
public interface MenuService {
    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    List<MenuNodeVO> findMenuTree();

    /**
     * 添加菜单
     *
     * @param menu 菜单信息
     * @return 菜单信息
     */
    Menu add(MenuDTO menu);

    /**
     * 删除节点
     *
     * @param id 菜单id
     */
    void delete(Long id);

    /**
     * 编辑节点
     *
     * @param id 菜单id
     * @return 菜单信息
     */
    MenuVO edit(Long id);

    /**
     * 更新节点
     *
     * @param id   菜单id
     * @param menu 菜单信息
     */
    void update(Long id, MenuVO menu);

    /**
     * 所有展开菜单的ID
     *
     * @return 菜单 id list
     */
    List<Long> findOpenIds();

    /**
     * 获取所有菜单
     *
     * @return 菜单list
     */
    List<Menu> findAll();
}
