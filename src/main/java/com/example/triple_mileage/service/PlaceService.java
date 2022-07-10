package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.entity.Place;
import com.example.triple_mileage.dto.PlaceSaveDto;
import com.example.triple_mileage.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
   public void save(PlaceSaveDto placeSaveDto){
        UUID placeId=UUID.fromString(placeSaveDto.getPlaceId());
        String placeName=placeSaveDto.getPlaceName();
       Place place=new Place();
       place.setPlaceId(placeId);
       place.setPlaceName(placeName);
       placeRepository.save(place);
   }
}
