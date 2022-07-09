package com.example.triple_mileage;

import com.example.triple_mileage.domain.Place;
import com.example.triple_mileage.domain.User;
import com.example.triple_mileage.repository.PlaceRepository;
import com.example.triple_mileage.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class PlaceTest {

    @Autowired
    PlaceRepository placeRepository;

    final String placeIdStr="2e4baf1c-5acb-4efb-a1af-eddada31b00f";

    Logger logger= LoggerFactory.getLogger(PlaceTest.class);

    @Test
    @Rollback(value = false)
    public void placeSaveTest(){
        Place place=new Place();

        UUID placeId=UUID.fromString(placeIdStr);
        place.setPlaceId(placeId);
        place.setPlaceName("신라스테이 강남");
        placeRepository.save(place);

        Place findPlace=placeRepository.findOne(placeId);
        logger.info("저장된 uuid:{}",findPlace.getPlaceId().toString());

        Assert.assertEquals(placeId,findPlace.getPlaceId());
        Assert.assertEquals(0,place.getReviews().size());
    }



}
