package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.entity.Place;
import com.example.triple_mileage.domain.entity.Review;
import com.example.triple_mileage.dto.place.PlaceSaveDto;
import com.example.triple_mileage.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public void savePlace(PlaceSaveDto placeSaveDto) {
        UUID placeId = UUID.fromString(placeSaveDto.getPlaceId());
        String placeName = placeSaveDto.getPlaceName();
        Place place = new Place();
        place.setPlaceId(placeId);
        place.setPlaceName(placeName);
        placeRepository.save(place);
    }

    @Transactional
    public void removePlace(String placeIdStr) {
        UUID placeId = UUID.fromString(placeIdStr);
        Place place = placeRepository.findOne(placeId);
        List<Review> reviewList = place.getReviewList();
        for (Review r : reviewList) {
            r.setPlace(null); //연관관계 끊어줌(장소가 사라져도 사용자의 리뷰는 남겨둠) - Review의 placeId=null로 세팅
        }
        placeRepository.removePlace(place);
    }
}
