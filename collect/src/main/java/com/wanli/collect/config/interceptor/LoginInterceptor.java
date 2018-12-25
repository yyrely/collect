package com.wanli.collect.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanli.collect.context.RequestContext;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.Contants;
import com.wanli.collect.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author Hu
 * @date 2018/12/25 15:18
 */

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${token.timeout}")
    private Integer tokenTimeout;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;


    public LoginInterceptor(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContext.setRequestTime(System.currentTimeMillis());
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            return true;
        }
        log.debug("===> method: {},path: {}", request.getMethod(), request.getRequestURI());
        String token = request.getHeader("token");
        String userJson = stringRedisTemplate.opsForValue().get(Contants.ONLINE_TOKEN + token);

        if (StringUtils.isBlank(userJson)) {
            log.error("===> user not login,token: {}", token);
            throw new ServiceException(BaseErrorCode.NOT_LOGIN);
        }

        if ("other login".equals(userJson)) {
            log.error("===> user other login,token: {}", token);
            throw new ServiceException(BaseErrorCode.OTHER_LOGIN);
        }

        User users = objectMapper.readValue(userJson, User.class);
        stringRedisTemplate.expire(Contants.ONLINE_USER + users.getUserUsername(), tokenTimeout, TimeUnit.SECONDS);
        stringRedisTemplate.expire(Contants.ONLINE_TOKEN + token, tokenTimeout, TimeUnit.SECONDS);

        RequestContext.setUserInfo(users);
        log.debug("===> check token,cost {}ms", System.currentTimeMillis() - RequestContext.getRequestTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.debug("<=== api cost {} ms.", System.currentTimeMillis() - RequestContext.getRequestTime());
    }
}
