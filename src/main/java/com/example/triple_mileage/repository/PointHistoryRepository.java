package com.example.triple_mileage.repository;

import com.example.triple_mileage.domain.entity.PointHistory;
import com.example.triple_mileage.response.PointDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepository {
    private final EntityManager em;

    public void save(PointHistory pointHistory){
        em.persist(pointHistory);
    }

    public PointHistory findOne(Long id){
        return em.find(PointHistory.class,id);
    }

    public Long calculatePoint(UUID userId){
        TypedQuery<Long> query=em.createQuery("SELECT sum(h.changedPoint) FROM PointHistory h where h.user.userId =:userId",Long.class)
                .setParameter("userId",userId);

        return query.getSingleResult();

    }
}
