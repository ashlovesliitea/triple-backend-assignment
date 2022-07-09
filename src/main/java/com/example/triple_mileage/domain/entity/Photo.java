package com.example.triple_mileage.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Photo {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID photoId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;


    public static Photo createPhoto(UUID photoId,Review review){
        Photo photo=new Photo();
        photo.setPhotoId(photoId);
        photo.setReview(review);
        return photo;
    }

}
