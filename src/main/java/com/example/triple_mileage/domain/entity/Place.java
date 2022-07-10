package com.example.triple_mileage.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Place {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private UUID placeId;

    @NotNull
    private String placeName;


    @OneToMany(mappedBy = "place")
    private List<Review> reviewList =new ArrayList<>();

}
