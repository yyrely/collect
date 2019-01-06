package com.wanli.collect.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private BigDecimal transducerMax;

    private BigDecimal transducerMin;

    private BigDecimal transducerErrornum;

    private BigDecimal transducerLevel;

    private Integer transducerWarntime;

    private LocalDateTime updateTime;
}
