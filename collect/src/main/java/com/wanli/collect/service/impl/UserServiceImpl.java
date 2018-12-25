package com.wanli.collect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.UserExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.Contants;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.dto.UserDTO;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.model.vo.UserVO;
import com.wanli.collect.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Hu
 * @date 2018/12/23 19:23
 */

@Service
public class UserServiceImpl implements UserService {

    @Value("${token.timeout}")
    private int tokenTimeout;


    private final ObjectMapper objectMapper;
    private final UserExtMapper userExtMapper;
    private final StringRedisTemplate redisTemplate;

    public UserServiceImpl(ObjectMapper objectMapper, UserExtMapper userExtMapper, StringRedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.userExtMapper = userExtMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object login(UserDTO userDTO) throws JsonProcessingException {
        //判断用户名和应用标识
        if(StringUtils.isEmpty(userDTO.getUserUsername()) || StringUtils.isEmpty(userDTO.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        //查找用户并判断
        User existUser = userExtMapper.findUserByUsernameAndFlag(userDTO.getUserUsername(), userDTO.getApplicationFlag());
        if(existUser == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        //判断密码
        if(!existUser.getUserPassword().equals(userDTO.getUserPassword())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        //防止重复登录
        String token = redisTemplate.opsForValue().get(Contants.ONLINE_USER + existUser.getUserUsername());
        if(!StringUtils.isEmpty(token)) {
            redisTemplate.expire(Contants.ONLINE_USER + userDTO.getUserUsername(), 0, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(Contants.ONLINE_TOKEN + token, "other login");
            redisTemplate.expire(Contants.ONLINE_TOKEN + token, 600, TimeUnit.SECONDS);
        }
        //在缓存中加入新的标识
        token = UUID.randomUUID().toString();
        String userJson = objectMapper.writeValueAsString(existUser);
        redisTemplate.opsForValue().set(Contants.ONLINE_TOKEN + token, userJson);
        redisTemplate.opsForValue().set(Contants.ONLINE_USER + existUser.getUserUsername(), token);
        redisTemplate.expire(Contants.ONLINE_TOKEN + token, tokenTimeout, TimeUnit.SECONDS);
        redisTemplate.expire(Contants.ONLINE_USER + existUser.getUserUsername(), tokenTimeout, TimeUnit.SECONDS);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(existUser,userVO);
        userVO.setToken(token);

        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object register(UserDTO userDTO) {

        User user = RequestContext.getUserInfo();

        //普通用户无权限
        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        //负责人创建子用户
        if(user.getUserStatus() == UserStatusType.CHARGE) {

            if(StringUtils.isEmpty(userDTO.getUserUsername())) {
                throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
            }
            checkUsername(userDTO.getUserUsername(), user.getApplicationFlag());

            newUser.setApplicationFlag(user.getApplicationFlag());
            newUser.setFatherId(user.getUserId());
            newUser.setUserStatus(UserStatusType.NORMAL);
            userExtMapper.insert(newUser);
            newUser.setUserPassword(null);
            return newUser;
        }

        //总管理创建负责人
        if(user.getUserStatus() == UserStatusType.GENERAL_MANAGER) {

            if(StringUtils.isEmpty(userDTO.getUserUsername()) || StringUtils.isEmpty(user.getApplicationFlag())) {
                throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
            }
            checkUsername(userDTO.getUserUsername(), userDTO.getApplicationFlag());
            newUser.setUserStatus(UserStatusType.CHARGE);
            newUser.setFatherId(0L);
            userExtMapper.insert(newUser);
            newUser.setUserPassword(null);
            return newUser;
        }

        throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void checkUsername(String username, String applicationFlag) {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(applicationFlag)){
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        User existUser = userExtMapper.findUserByUsernameAndFlag(username, applicationFlag);
        if(existUser != null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object findUserInfo(Long userId) {
        return userExtMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Object logout() {
        User users = RequestContext.getUserInfo();
        String token = redisTemplate.opsForValue().get(Contants.ONLINE_USER + users.getUserUsername());

        redisTemplate.expire(Contants.ONLINE_USER + users.getUserUsername(), 0, TimeUnit.SECONDS);
        redisTemplate.expire(Contants.ONLINE_TOKEN + token, 0, TimeUnit.SECONDS);

        return null;
    }

    @Override
    public Object listUsers() {

        User user = RequestContext.getUserInfo();

        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(user.getUserStatus() == UserStatusType.CHARGE) {
            List<User> list = userExtMapper.listUsersByFlagAndStatus(UserStatusType.NORMAL, user.getApplicationFlag());
            return list;
        }

        if(user.getUserStatus() == UserStatusType.GENERAL_MANAGER) {
            List<UserVO> list = userExtMapper.listChargeUsers(UserStatusType.CHARGE);
            return list;
        }

        return null;
    }
}




























