package com.wanli.collect.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Create By HU
 * Date 2019/1/5 19:17
 */

@Data
public class CollectData {

    private String transducerId;

    private String boardId;

    private String transducerType;

    private BigDecimal transducerData;

    private LocalDateTime dataTime;
}
