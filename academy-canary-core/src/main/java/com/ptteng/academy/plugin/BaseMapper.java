package com.ptteng.academy.plugin;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: canary
 * @description: mybatis 通用sql语句插件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 02:53
 **/

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
    // 特别注意，该接口不能被扫描到，否则会出错
    // 只要继承 Mapper<T> 这个接口, 实现本接口的类就会引入mybatis的通用查询方法.
    // 只要继承 MySqlMapper<T> 这个接口, 实现本接口的类就会被支持直接执行sql语句.
}
