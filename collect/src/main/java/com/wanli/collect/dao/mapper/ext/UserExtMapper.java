package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserExtMapper extends com.wanli.collect.dao.mapper.UserMapper {

    /**
     * 根据用户名查找用户
     * @param userUsername
     * @param applicationFlag
     * @return
     */
    User findUserByUsernameAndFlag(
            @Param("username") String userUsername,
            @Param("applicationFlag") String applicationFlag);

    /**
     * 根据用户角色和应用标识
     * @param fatherId
     * @return
     */
    List<User> listUsersByFatherId(
            @Param("fatherId") Long fatherId);

    /**
     * 获取负责人和应用信息
     * @param status
     * @return
     */
    List<UserVO> listChargeUsers(@Param("status") UserStatusType status);

    /**
     * 根据应用id删除所有用户信息
     * @param applicationFlag
     */
    void removeAllUserByApplicationFlag(String applicationFlag);

    /**
     * 根据应用id获取所有用户信息
     * @param applicationFlag
     * @return
     */
    List<User> listUsersByApplicationFlag(String applicationFlag);
}