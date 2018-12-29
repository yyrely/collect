package com.wanli.collect.model.dto;

import com.wanli.collect.model.constants.DataStatusType;
import com.wanli.collect.model.entity.TransducerDataConf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Hu
 * @date 2018/12/29 9:37
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransducerDTO {

    private Long id;

    private String boardId;

    private String transducerType;

    private String transducerUnit;

    private String transducerId;

    private DataStatusType transducerStatus;

    private BigDecimal transducerNowdata;

    /**
     * 数据最新更新时间
     */
    private Instant transducerTime;

    private String transducerDescription;

    private TransducerDataConf transducerDataConf;

}
