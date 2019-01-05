package com.wanli.collect.entity;

import lombok.Data;

import java.time.Instant;

/**
 * Create By HU
 * Date 2019/1/5 17:06
 */

@Data
public class TransducerConf {

    private Long id;

    private String applicationFlag;

    private String boardId;

    private String transducerType;

    private String transducerId;

    private Double transducerMax;

    private Double transducerMin;

    private Double transducerErrornum;

    private Double transducerLevel;

    private Integer transducerWarntime;

    private Instant updateTime;
}
