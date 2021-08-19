package com.wupao.oneclickrelease.service;

import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于递归构建树形菜单
 *
 * @author wuxianglong
 */
public class MenuTreeBuilder {

    public static List<MenuNodeVO> build(List<MenuNodeVO> nodes) {
        List<MenuNodeVO> rootMenu = new ArrayList<>();
        for (MenuNodeVO nav : nodes) {
            if (nav.getParentId() == 0) {
                rootMenu.add(nav);
            }
        }
        rootMenu.sort(MenuNodeVO.order());
        for (MenuNodeVO nav : rootMenu) {
            List<MenuNodeVO> childList = getChild(nav.getId(), nodes);
            nav.setChildren(childList);
        }
        return rootMenu;
    }


    private static List<MenuNodeVO> getChild(Long id, List<MenuNodeVO> nodes) {
        List<MenuNodeVO> childList = new ArrayList<>();
        for (MenuNodeVO nav : nodes) {
            if (nav.getParentId().equals(id)) {
                childList.add(nav);
            }
        }
        for (MenuNodeVO nav : childList) {
            nav.setChildren(getChild(nav.getId(), nodes));
        }
        childList.sort(MenuNodeVO.order());
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }


}
