package com.example.triple_mileage.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDeleteDto {
    private String userId;
    private String reviewId;
}
