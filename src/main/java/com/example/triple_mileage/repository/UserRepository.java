package com.example.triple_mileage.repository;

import com.example.triple_mileage.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
       em.persist(user);
    }
    public User findUser(UUID id){
        return em.find(User.class,id);
    }

    public String findName(UUID userId){
        TypedQuery<String> query=em.createQuery("SELECT u.userName FROM User u where u.userId =:userId",String.class)
                .setParameter("userId",userId);

        return query.getSingleResult();
    }
}
