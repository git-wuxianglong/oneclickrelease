package com.wupao.oneclickrelease.service;

import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;
import com.wupao.oneclickrelease.vo.permission.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 菜单转换
 *
 * @author wuxianglong
 */
public class MenuConverter {

    public static List<MenuNodeVO> converterToMenuNodeVO(List<Menu> menus) {
        List<MenuNodeVO> menuNodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (menu.getType() == 0) {
                    MenuNodeVO menuNodeVO = new MenuNodeVO();
                    BeanUtils.copyProperties(menu, menuNodeVO);
                    menuNodeVO.setDisabled(menu.getAvailable() == 0);
                    menuNodeList.add(menuNodeVO);
                }
            }
        }
        return menuNodeList;
    }

    public static List<MenuNodeVO> converterToALLMenuNodeVO(List<Menu> menus) {
        List<MenuNodeVO> menuNodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                MenuNodeVO menuNodeVO = new MenuNodeVO();
                BeanUtils.copyProperties(menu, menuNodeVO);
                menuNodeVO.setDisabled(menu.getAvailable() == 0);
                menuNodeList.add(menuNodeVO);
            }
        }
        return menuNodeList;
    }

    public static MenuVO converterToMenuVO(Menu menu) {
        MenuVO menuVO = new MenuVO();
        if (menu != null) {
            BeanUtils.copyProperties(menu, menuVO);
            menuVO.setDisabled(menu.getAvailable() == 0);
        }
        return menuVO;
    }

}
