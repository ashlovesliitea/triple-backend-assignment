package com.example.triple_mileage.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Slf4j
public class Review {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;

    //private ReviewStatus reviewStatus;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Photo> photos=new ArrayList<>();

    private int contentPoint;
    private int photoPoint;
    private int bonusPoint;

    private ZonedDateTime date;



    //ManyToOne - 연관관계 편의 메소드
    public void setUser(User user){
        this.user=user;
        user.getReviewList().add(this);
    }

    public void setPlace(Place place){
        this.place=place;
        place.getReviews().add(this);
    }



    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setReview(this);
    }



    public void modifyContents(String content){
        this.setContent(content);
    }


    //리뷰의 총 포인트 계산
    public int totalPoint(){
        return contentPoint+photoPoint+bonusPoint;
    }


}
