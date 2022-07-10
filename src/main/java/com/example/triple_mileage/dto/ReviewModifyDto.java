package com.example.triple_mileage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ReviewModifyDto {
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;

}
