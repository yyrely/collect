package com.wanli.collect.model.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Hu
 * @date 2018/12/25 10:33
 */

public enum UserStatusType {

    /**
     * 0.未知
     */
    UNKNOWN("未知"),

    /**
     * 1.普通
     */
    NORMAL("普通"),

    /**
     * 2.负责人
     */
    CHARGE("负责人"),

    /**
     * 3.总管理
     */
    GENERAL_MANAGER("总管理");

    private String value;

    UserStatusType(String value) {
        this.value = value;
    }

    @JsonValue
    private String getValue(){
        return value;
    }

}
