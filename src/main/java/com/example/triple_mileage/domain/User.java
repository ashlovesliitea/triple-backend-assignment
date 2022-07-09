package com.example.triple_mileage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PointHistory> pointHistories;

    public void addPointHistory(PointHistory pointHistory){
        pointHistories.add(pointHistory);
        pointHistory.setUser(this);
    }
}
