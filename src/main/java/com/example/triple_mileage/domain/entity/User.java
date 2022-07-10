package com.example.triple_mileage.domain.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private UUID userId;

    @Column(length = 10)
    @NotNull
    private String userName;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PointHistory> pointHistories;

    public void addPointHistory(PointHistory pointHistory){
        pointHistories.add(pointHistory);
        pointHistory.setUser(this);
    }
}
