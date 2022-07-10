package com.example.triple_mileage.controller;

import com.example.triple_mileage.dto.UserDto;
import com.example.triple_mileage.dto.UserSaveDto;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class UserController {
    private final UserService userService;
    private final Logger logger= LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("/user")
    public ResponseObj<String> createUser(@RequestBody UserDto userDTO){
        UserSaveDto userSaveDto=new UserSaveDto(userDTO.getUserId(),userDTO.getUserName());
        userService.save(userSaveDto);
        return new ResponseObj<>("새로운 유저를 생성했습니다.");
    }
}
