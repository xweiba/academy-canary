package com.ptteng.academy.business.enums;

/**
 * @program: canary
 * @description: 学科
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 13:31
 **/

public enum  SubjectEnum {
    ONE(1, "语文"), TOW(2, "数学"), THREE(3, "英语"),
    FOUR(4, "物理"), FIVE(5, "化学"), SIX(6, "生物"), ALL(0, "全部");

    private int code;
    private String subject;
    SubjectEnum(Integer code, String subject) {
        this.code = code;
        this.subject = subject;
    }

    // 通过 code 获取
    public static SubjectEnum getSubjectEnum(Integer code) {
        if (code == null |  0 == code) {
            return ALL;
        }
        for (SubjectEnum grade :
                SubjectEnum.values()) {
            if (grade.getCode() == code) {
                return grade;
            }
        }
        return null;
    }
    public static SubjectEnum getSubjectEnum(String enumName) {
        if (enumName == null | "".equals(enumName)) {
            return ALL;
        }
        if ("one".equals(enumName)) {
            return ONE;
        }
        if ("tow".equals(enumName)) {
            return TOW;
        }
        if ("three".equals(enumName)) {
            return THREE;
        }
        if ("four".equals(enumName)) {
            return FOUR;
        }
        if ("five".equals(enumName)) {
            return FIVE;
        }
        if ("six".equals(enumName)) {
            return SIX;
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
