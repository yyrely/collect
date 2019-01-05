package com.wanli.collect.entity;

import lombok.Data;

import java.time.Instant;

/**
 * Create By HU
 * Date 2019/1/5 19:17
 */

@Data
public class CollectData {

    private String transducerId;

    private String boardId;

    private String transducerType;

    private Double transducerData;

    private Instant dataTime;
}
