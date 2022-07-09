package com.example.triple_mileage.repository;

import com.example.triple_mileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
}
