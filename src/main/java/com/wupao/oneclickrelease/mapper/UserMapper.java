package com.wupao.oneclickrelease.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wupao.oneclickrelease.dto.user.UserSearchDTO;
import com.wupao.oneclickrelease.pojo.User;
import com.wupao.oneclickrelease.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuxianglong
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号查询用户
     *
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    User findUserByMobile(@Param("phoneNumber") String phoneNumber);

    /**
     * 分页查询用户列表
     *
     * @param page       分页
     * @param userSearch 搜索条件
     * @return 用户列表
     */
    IPage<UserVO> findUserList(Page<?> page, @Param("param") UserSearchDTO userSearch);

}
