package com.ptteng.academy.framework.pojo;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @program: canary
 * @description: 通用接口
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-25 17:06
 **/

public interface AbstractService<T,PK> {
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    T insert(T entity) throws Exception;

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    void insertList(List<T> entities) throws Exception;

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    boolean removeByPrimaryKey(PK primaryKey) throws Exception;

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    boolean update(T entity) throws Exception;
    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    boolean updateByPrimaryKeySelective(T entity) throws FileNotFoundException, Exception;


    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    T getByPrimaryKey(PK primaryKey) throws Exception;

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    T getOneByEntity(T entity) throws Exception;

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    List<T> listAll() throws Exception;

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    List<T> listByEntity(T entity) throws Exception;
}
