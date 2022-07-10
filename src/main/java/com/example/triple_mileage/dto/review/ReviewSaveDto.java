package com.example.triple_mileage.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ReviewSaveDto {
    private String reviewId;
    private String userId;
    private String placeId;
    private String content;
    private List<String> attachedPhotoIds;
}
