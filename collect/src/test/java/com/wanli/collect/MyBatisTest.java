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
        System.out.println(myAtoi("2147483648"));
    }


    public int myAtoi(String str) {
        int i = 0;
        for(i = 0;i < str.length();i++){
            if((int)str.charAt(i) != 32){
                break;
            }
        }
        int flag = 43;
        int result = 0;
        for(int j = i;j < str.length();j++) {
            int k = (int)str.charAt(j);
            if((k == 43 || k == 45) && j == i){
                flag = (int)str.charAt(j);
                continue;
            }
            if(k >= 48 && k <= 57) {
                if (result > Integer.MAX_VALUE/10 || (result == Integer.MAX_VALUE / 10 && Integer.parseInt(str.charAt(j)+"") > 7)) {
                    if(flag == 43) {
                        return Integer.MAX_VALUE;
                    }else {
                        return Integer.MIN_VALUE;
                    }
                }
                result = result * 10 + Integer.parseInt(str.charAt(j)+"");
            }else{
                break;
            }
        }
        if(flag == 43) {
            return result * 1;
        }else {
            return result * (-1);
        }

    }



}
