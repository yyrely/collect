package com.wanli.collect.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Create By HU
 * Date 2019/1/5 19:18
 */

@Data
public class Transducer {

    private Long id;

    private String boardId;

    private String transducerType;

    private String transducerId;

    private Byte transducerStatus;

    private BigDecimal transducerNowdata;

    private LocalDateTime transducerTime;
}
