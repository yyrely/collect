package com.wanli.collect;

import com.wanli.collect.dao.mapper.ext.UserExtMapper;
import com.wanli.collect.model.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Hu
 * @date 2019/2/25 15:51
 */

public class MyBatisTest {

    @Test
    public void test() throws IOException {

        InputStream is = Resources.getResourceAsStream("conf.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        String statement = "com.wanli.collect.dao.mapper.ext.UserExtMapper.findUserByUsernameAndFlag";
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserExtMapper mapper = sqlSession.getMapper(UserExtMapper.class);
        mapper.removeAllUserByApplicationFlag("0");
        User user = new User();
        user.setUserUsername("admin");
        user.setApplicationFlag("0");
        sqlSession.selectOne(statement, 1);
    }

    @Test
    public void test1() {

    }

}
