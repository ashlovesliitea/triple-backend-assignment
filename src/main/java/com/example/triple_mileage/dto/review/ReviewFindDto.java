package com.example.triple_mileage.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewFindDto {

    private String place_name;
    private String user_name;
    private String content;
    private List<String> attachedPhotoIds;
    private ZonedDateTime date;
}
