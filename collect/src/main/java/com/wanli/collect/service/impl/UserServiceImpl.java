package com.wanli.collect.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ch.qos.logback.core.joran.conditional.ElseAction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public Object login(UserDTO userDTO) throws Exception {
        //判断用户名
        if(StringUtils.isEmpty(userDTO.getUsername())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        //查找用户并判断
        User existUser = userExtMapper.findUserByUsernameAndFlag(userDTO.getUsername(), userDTO.getApplicationFlag());
        if(existUser == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        //判断密码
        if(!existUser.getUserPassword().equals(userDTO.getPassword())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        //防止重复登录
        String token = redisTemplate.opsForValue().get(Contants.ONLINE_USER + existUser.getApplicationFlag() + existUser.getUserUsername());
        if(!StringUtils.isEmpty(token)) {
            redisTemplate.expire(Contants.ONLINE_USER + existUser.getApplicationFlag() + existUser.getUserUsername(), 0, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(Contants.ONLINE_TOKEN + token, "other login");
            redisTemplate.expire(Contants.ONLINE_TOKEN + token, 600, TimeUnit.SECONDS);
        }
        //在缓存中加入新的标识
        token = UUID.randomUUID().toString();
        String userJson = objectMapper.writeValueAsString(existUser);
        redisTemplate.opsForValue().set(Contants.ONLINE_TOKEN + token, userJson);
        redisTemplate.opsForValue().set(Contants.ONLINE_USER + existUser.getApplicationFlag() + existUser.getUserUsername(), token);
        redisTemplate.expire(Contants.ONLINE_TOKEN + token, tokenTimeout, TimeUnit.SECONDS);
        redisTemplate.expire(Contants.ONLINE_USER + existUser.getApplicationFlag() + existUser.getUserUsername(), tokenTimeout, TimeUnit.SECONDS);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(existUser,userVO);
        userVO.setToken(token);
        userVO.setUserPassword(null);

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

        if(StringUtils.isEmpty(userDTO.getUsername())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        if(StringUtils.isEmpty(userDTO.getPassword())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        User newUser = new User();


        newUser.setUserUsername(userDTO.getUsername());
        newUser.setUserPassword(userDTO.getPassword());
        //负责人创建子用户
        if(user.getUserStatus() == UserStatusType.CHARGE) {

            checkUsername(userDTO.getUsername(), user.getApplicationFlag());

            newUser.setApplicationFlag(user.getApplicationFlag());
            newUser.setFatherId(user.getUserId());
            newUser.setUserStatus(UserStatusType.NORMAL);
            userExtMapper.insert(newUser);
            return newUser;
        }

        //总管理创建负责人
        if(user.getUserStatus() == UserStatusType.GENERAL_MANAGER) {

            if(StringUtils.isEmpty(user.getApplicationFlag())) {
                throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
            }
            checkUsername(userDTO.getUsername(), userDTO.getApplicationFlag());
            newUser.setApplicationFlag(userDTO.getApplicationFlag());
            newUser.setUserStatus(UserStatusType.CHARGE);
            newUser.setFatherId(user.getUserId());
            userExtMapper.insert(newUser);
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
    public Object findUser(Long userId) {
        User user = userExtMapper.selectByPrimaryKey(userId);
        user.setUserPassword(null);
        return user;
    }

    @Override
    public Object logout() {
        User user = RequestContext.getUserInfo();
        String token = redisTemplate.opsForValue().get(Contants.ONLINE_USER + user.getApplicationFlag() + user.getUserUsername());

        redisTemplate.expire(Contants.ONLINE_USER + user.getApplicationFlag() + user.getUserUsername(), 0, TimeUnit.SECONDS);
        redisTemplate.expire(Contants.ONLINE_TOKEN + token, 0, TimeUnit.SECONDS);

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object listUsers() {

        User user = RequestContext.getUserInfo();

        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        //负责人子用户列表
        if(user.getUserStatus() == UserStatusType.CHARGE) {
            return userExtMapper.listUsersByFatherId(user.getUserId());
        }

        //总管理下的负责人列表(多返回应用信息)
        if(user.getUserStatus() == UserStatusType.GENERAL_MANAGER) {
            return userExtMapper.listChargeUsers(UserStatusType.CHARGE);
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updatePassword(Long userId, UserDTO userDTO) {

        User user = RequestContext.getUserInfo();

        if(!user.getUserId().equals(userId)) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        User userInfo = userExtMapper.selectByPrimaryKey(userId);

        if(!userInfo.getUserPassword().equals(userDTO.getPassword())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        userInfo.setUserPassword(userDTO.getNewPassword());
        userExtMapper.updateByPrimaryKeySelective(userInfo);

        //已登录用户退出登录
        logout();

        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateUser(Long userId, UserDTO userDTO) {

        User user = RequestContext.getUserInfo();

        if(StringUtils.isEmpty(userDTO.getPassword())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        User userInfo = userExtMapper.selectByPrimaryKey(userId);
        if(userInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        if(!userInfo.getFatherId().equals(user.getUserId())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        userInfo.setUserPassword(userDTO.getPassword());
        userExtMapper.updateByPrimaryKeySelective(userInfo);

        //修改密码的用户退出登录
        delToken(userInfo);
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object removeUser(Long userId) {

        User user = RequestContext.getUserInfo();

        User userInfo = userExtMapper.selectByPrimaryKey(userId);
        if(userInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        if(!userInfo.getFatherId().equals(user.getUserId())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }
        //总管理
        if(user.getUserStatus() == UserStatusType.GENERAL_MANAGER) {
            List<User> userList = userExtMapper.listUsersByApplicationFlag(userInfo.getApplicationFlag());
            userExtMapper.removeAllUserByApplicationFlag(userInfo.getApplicationFlag());
            userList.forEach(this::delToken);
        }else if (user.getUserStatus() == UserStatusType.CHARGE) {
            userExtMapper.deleteByPrimaryKey(userId);
            delToken(userInfo);
        }
        return null;
    }

    private void delToken(User user) {
        String token = redisTemplate.opsForValue().get(Contants.ONLINE_USER + user.getApplicationFlag() + user.getUserUsername());
        if(!StringUtils.isEmpty(token)) {
            redisTemplate.expire(Contants.ONLINE_USER + user.getApplicationFlag() + user.getUserUsername(), 0, TimeUnit.SECONDS);
            redisTemplate.expire(Contants.ONLINE_TOKEN + token, 0, TimeUnit.SECONDS);
        }
    }

}



























