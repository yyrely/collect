package com.wanli.collect.dao.mapper;

import com.wanli.collect.model.entity.TransducerType;

public interface TransducerTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transducer_type
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transducer_type
     *
     * @mbg.generated
     */
    int insert(TransducerType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transducer_type
     *
     * @mbg.generated
     */
    int insertSelective(TransducerType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transducer_type
     *
     * @mbg.generated
     */
    TransducerType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transducer_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TransducerType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transducer_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TransducerType record);
}