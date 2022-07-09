package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.entity.Place;
import com.example.triple_mileage.domain.entity.User;
import com.example.triple_mileage.repository.PlaceRepository;
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
   public void save(UUID userId,String userName){
       User user=new User();
       user.setUserId(userId);
       user.setUserName(userName);
       userRepository.save(user);
   }
}
