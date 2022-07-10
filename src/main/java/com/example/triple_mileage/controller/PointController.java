package com.example.triple_mileage.controller;

import com.example.triple_mileage.dto.PointDto;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class PointController {
    private final PointService pointService;
    private final Logger logger= LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @GetMapping("/point-calculate")
    public ResponseObj<PointDto> createPlace(@RequestParam(value="user-id") String userId){
        PointDto point =pointService.calculatePoint(userId);
        return new ResponseObj<>(point);
    }
}
