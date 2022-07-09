package com.example.triple_mileage.repository;

import com.example.triple_mileage.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {
    private final EntityManager em;


    public void save(Photo photo){
        em.persist(photo);
    }

    public Photo findPhoto(UUID photo) {
        return em.find(Photo.class,photo);
    }
}
