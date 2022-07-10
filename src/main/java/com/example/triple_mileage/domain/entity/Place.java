package com.example.triple_mileage.domain.entity;

import com.sun.istack.NotNull;
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
    @NotNull
    private UUID placeId;

    @NotNull
    private String placeName;


    @OneToMany(mappedBy = "place")
    private List<Review> reviews=new ArrayList<>();

}
