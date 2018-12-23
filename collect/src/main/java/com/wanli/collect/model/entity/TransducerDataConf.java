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
public class TransducerDataConf {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.board_id
     *
     * @mbg.generated
     */
    private String boardId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_type
     *
     * @mbg.generated
     */
    private String transducerType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_id
     *
     * @mbg.generated
     */
    private String transducerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.application_flag
     *
     * @mbg.generated
     */
    private String applicationFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_max
     *
     * @mbg.generated
     */
    private Double transducerMax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_min
     *
     * @mbg.generated
     */
    private Double transducerMin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_errornum
     *
     * @mbg.generated
     */
    private Double transducerErrornum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_level
     *
     * @mbg.generated
     */
    private Double transducerLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_unit
     *
     * @mbg.generated
     */
    private String transducerUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.transducer_warntime
     *
     * @mbg.generated
     */
    private Long transducerWarntime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.update_time
     *
     * @mbg.generated
     */
    private Instant updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transducer_data_conf.conf_description
     *
     * @mbg.generated
     */
    private String confDescription;
}