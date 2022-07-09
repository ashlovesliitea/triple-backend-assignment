package com.example.triple_mileage.controller;

import com.example.triple_mileage.request.UserDTO;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.ReviewService;
import com.example.triple_mileage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class UserController {
    private final UserService userService;
    private final Logger logger= LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("/user")
    public ResponseObj<String> createUser(@RequestBody UserDTO userDTO){

        userService.save(UUID.fromString(userDTO.getUserId()),userDTO.getUserName());
        return new ResponseObj<>("새로운 유저를 생성했습니다.");
    }
}
