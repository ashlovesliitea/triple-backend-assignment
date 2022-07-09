package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.entity.Place;
import com.example.triple_mileage.repository.PlaceRepository;
import com.example.triple_mileage.repository.PointHistoryRepository;
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
   public void save(UUID placeId,String placeName){
       Place place=new Place();
       place.setPlaceId(placeId);
       place.setPlaceName(placeName);
       placeRepository.save(place);
   }
}
