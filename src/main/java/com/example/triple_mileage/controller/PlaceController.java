package com.example.triple_mileage.controller;

import com.example.triple_mileage.dto.place.PlaceDto;
import com.example.triple_mileage.dto.place.PlaceSaveDto;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
@Slf4j
public class PlaceController {
    private final PlaceService placeService;
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("")
    public ResponseObj<String> createPlace(@RequestBody PlaceDto placeDTO) {
        PlaceSaveDto placeSaveDto = new PlaceSaveDto(placeDTO.getPlaceId(), placeDTO.getPlaceName());
        placeService.savePlace(placeSaveDto);
        return new ResponseObj<>("새로운 장소를 생성했습니다.");
    }
}
