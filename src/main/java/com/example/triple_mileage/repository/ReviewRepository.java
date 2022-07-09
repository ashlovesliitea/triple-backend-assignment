package com.example.triple_mileage.repository;

import com.example.triple_mileage.domain.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review){
        em.persist(review);
    }

    public Review findOne(UUID reviewId){
        return em.find(Review.class,reviewId);

    }

    public void remove(Review review) {
        em.remove(review);
    }
}
