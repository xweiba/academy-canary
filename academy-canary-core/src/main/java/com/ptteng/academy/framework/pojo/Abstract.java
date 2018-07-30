package com.ptteng.academy.framework.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 通用Do属性, 通用插件不会自动映射父类字段..
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 01:43
 **/

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Abstract implements Serializable{
    private static final long serialVersionUID = 6343087877294846798L;

    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date create_at;
    private Date update_at;
    private String create_by;
    private String update_by;
}
