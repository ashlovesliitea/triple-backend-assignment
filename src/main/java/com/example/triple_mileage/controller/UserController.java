package com.example.triple_mileage.controller;

import com.example.triple_mileage.dto.user.PointDto;
import com.example.triple_mileage.dto.user.UserDto;
import com.example.triple_mileage.dto.user.UserSaveDto;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.PointService;
import com.example.triple_mileage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final PointService pointService;
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("")
    public ResponseObj<String> createUser(@RequestBody UserDto userDTO) {
        UserSaveDto userSaveDto = new UserSaveDto(userDTO.getUserId(), userDTO.getUserName());
        userService.save(userSaveDto);
        return new ResponseObj<>("새로운 유저를 생성했습니다.");
    }


    @ResponseBody
    @GetMapping("/{userId}/total-point")
    public ResponseObj<PointDto> createPlace(@PathVariable("userId") String userId) {
        PointDto point = pointService.calculatePoint(userId);
        return new ResponseObj<>(point);
    }
}
