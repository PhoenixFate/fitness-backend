package com.phoenix.fitness;

import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void test() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1L);
        String token = jwtUtils.generateToken(userEntity);
        System.out.println(token);
    }

}
