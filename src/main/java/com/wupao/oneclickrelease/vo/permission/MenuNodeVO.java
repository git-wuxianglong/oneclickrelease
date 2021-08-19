package com.wupao.oneclickrelease.vo.permission;

import lombok.Data;

import java.util.Comparator;
import java.util.List;

/**
 * @author wuxianglong
 */
@Data
public class MenuNodeVO {
    private Long id;
    private Long parentId;
    private String menuName;
    private String url;
    private String icon;
    private Long orderNum;
    private Integer open;
    private Boolean disabled;
    private String perms;
    private Integer type;
    private List<MenuNodeVO> children;

    public static Comparator<MenuNodeVO> order() {
        return (o1, o2) -> {
            if (!o1.getOrderNum().equals(o2.getOrderNum())) {
                return (int) (o1.getOrderNum() - o2.getOrderNum());
            }
            return 0;
        };
    }
}
