package com.wanli.collect;

import com.wanli.collect.controller.UserController;
import com.wanli.collect.dao.mapper.BoardMapper;
import com.wanli.collect.dao.mapper.UserMapper;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.dao.mapper.ext.UserExtMapper;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectApplicationTests {

    @Autowired
    private BoardExtMapper boardExtMapper;

    @Test
    public void test(){

        boardExtMapper.selectByPrimaryKey("1");
    }

}

