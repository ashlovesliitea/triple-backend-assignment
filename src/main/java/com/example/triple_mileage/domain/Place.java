package com.example.triple_mileage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
