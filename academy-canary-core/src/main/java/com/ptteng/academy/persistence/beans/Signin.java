package com.ptteng.academy.persistence.beans;

import com.ptteng.academy.framework.pojo.Abstract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * description:
 * author:Lin
 * Date:2018/7/26
 * Time:16:59
 */
@Data
public class Signin implements Serializable {
    private static final long serialVersionUID = -8128905307559904110L;
    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date create_at;
    private Date update_at;
    private String create_by;
    private String update_by;
    // 签到详情
    private Long signinHistory;
    // 最后签到时间
    private Long lastSigninTime;
    // 签到总次数
    private Integer signinCount;
    // 最高连续签到次数
    private Integer topContinuouSignin;
}
