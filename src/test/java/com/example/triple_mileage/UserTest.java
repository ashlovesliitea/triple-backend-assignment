package com.example.triple_mileage;

import com.example.triple_mileage.domain.User;
import com.example.triple_mileage.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class UserTest {

    @Autowired
    UserRepository userRepository;


    Logger logger= LoggerFactory.getLogger(UserTest.class);

    @Test
    @Rollback(value = false)
    public void userSaveTest(){
        User user=new User();
        String userIdStr="3ede0ef2-92b7-4817-a5f3-0c575361f745";
        UUID userId=UUID.fromString(userIdStr);
        user.setUserId(userId);
        user.setUserName("트리플");
        userRepository.save(user);

        User findUser=userRepository.findUser(userId);
        logger.info("저장된 uuid:{}",findUser.getUserId().toString());

        Assert.assertEquals(userId,findUser.getUserId());
    }


}
