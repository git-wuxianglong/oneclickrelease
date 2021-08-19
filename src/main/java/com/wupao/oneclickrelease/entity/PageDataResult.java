package com.wupao.oneclickrelease.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 分页返回数据
 *
 * @author wuxianglong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDataResult<T> {
    /**
     * 总记录数量
     */
    private Integer total;
    /**
     * 当前页数据列表
     */
    private List<T> list;

    private final Integer code = 200;

}
