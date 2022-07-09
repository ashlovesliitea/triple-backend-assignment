package com.example.triple_mileage.controller;

import com.example.triple_mileage.request.PlaceDTO;
import com.example.triple_mileage.request.UserDTO;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.PlaceService;
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
public class PlaceController {
    private final PlaceService placeService;
    private final Logger logger= LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("/place")
    public ResponseObj<String> createPlace(@RequestBody PlaceDTO placeDTO){

        placeService.save(UUID.fromString(placeDTO.getPlaceId()), placeDTO.getPlaceName());
        return new ResponseObj<>("새로운 장소를 생성했습니다.");
    }
}
