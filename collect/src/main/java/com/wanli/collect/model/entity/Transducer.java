package com.wanli.collect.model.entity;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transducer {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.application_flag
     *
     * @mbg.generated
     */
    private String applicationFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.board_id
     *
     * @mbg.generated
     */
    private String boardId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_type
     *
     * @mbg.generated
     */
    private String transducerType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_id
     *
     * @mbg.generated
     */
    private String transducerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_status
     *
     * @mbg.generated
     */
    private Integer transducerStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_nowdata
     *
     * @mbg.generated
     */
    private Double transducerNowdata;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_unit
     *
     * @mbg.generated
     */
    private String transducerUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_time
     *
     * @mbg.generated
     */
    private Instant transducerTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer.transducer_description
     *
     * @mbg.generated
     */
    private String transducerDescription;
}