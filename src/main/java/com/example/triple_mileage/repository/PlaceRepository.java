package com.example.triple_mileage.repository;

import com.example.triple_mileage.domain.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {
    private final EntityManager em;

    public void save(Place place){
        em.persist(place);
    }

    public Place findOne(UUID id){
        return em.find(Place.class,id);

    }

}