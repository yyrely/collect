package com.wanli.collect.model.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Hu
 * @date 2018/12/27 10:13
 */

public enum DataStatusType {

    /**
     * 0.未知
     */
    UNKNOWN("未知"),

    /**
     * 1.正常
     */
    NORMAL("正常"),

    /**
     * 2.过高
     */
    HIGH("过高"),


    /**
     * 3.过低
     */
    LOW("过低");

    private String value;

    DataStatusType(String value) {
        this.value = value;
    }

    @JsonValue
    private String getValue(){
        return value;
    }



}
