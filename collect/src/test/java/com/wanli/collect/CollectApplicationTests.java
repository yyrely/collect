package com.wanli.collect;

import com.wanli.collect.dao.mapper.UserMapper;
import com.wanli.collect.dao.mapper.ext.UserExtMapper;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectApplicationTests {

    @Autowired
    private UserExtMapper userMapper;

    @Test
    public void contextLoads() {

        List<User> users = userMapper.listUsersByApplicationFlag("6666");
        users.forEach(u -> System.out.println(u));

    }

}

