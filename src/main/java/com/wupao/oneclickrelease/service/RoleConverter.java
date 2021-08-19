package com.wupao.oneclickrelease.service;

import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.vo.permission.RoleTransferItemVO;
import com.wupao.oneclickrelease.vo.permission.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色转换
 *
 * @author wuxianglong
 */
public class RoleConverter {

    public static List<RoleVO> converterToRoleVOList(List<Role> roles) {
        List<RoleVO> roleList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role, roleVO);
                roleVO.setStatus(role.getStatus() == 0);
                roleList.add(roleVO);
            }
        }
        return roleList;
    }

    public static List<RoleTransferItemVO> converterToRoleTransferItem(List<Role> list) {
        List<RoleTransferItemVO> itemVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Role role : list) {
                RoleTransferItemVO item = new RoleTransferItemVO();
                item.setLabel(role.getRoleName());
                item.setDisabled(role.getStatus() == 0);
                item.setKey(role.getId());
                itemVOList.add(item);
            }
        }
        return itemVOList;
    }
}
