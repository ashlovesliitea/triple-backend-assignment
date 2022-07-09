package com.example.triple_mileage.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Place {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID placeId;

    private String placeName;


    @OneToMany(mappedBy = "place",cascade = CascadeType.ALL)
    private List<Review> reviews=new ArrayList<>();

}
