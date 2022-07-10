package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.entity.User;
import com.example.triple_mileage.dto.UserSaveDto;
import com.example.triple_mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
   public void save(UserSaveDto userSaveDto){
        UUID userId=UUID.fromString(userSaveDto.getUserId());
        String userName=userSaveDto.getUserName();
       User user=new User();
       user.setUserId(userId);
       user.setUserName(userName);
       userRepository.save(user);
   }
}
