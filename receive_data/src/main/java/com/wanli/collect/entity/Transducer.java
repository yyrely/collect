package com.wanli.collect.entity;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

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

    private Integer transducerStatus;

    private Double transducerNowdata;

    private Instant transducerTime;
}
