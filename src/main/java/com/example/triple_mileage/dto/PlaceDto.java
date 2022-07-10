package com.example.triple_mileage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class PlaceDto {

    @NotBlank(message="반드시 uuid 형식의 id를 지정해주세요!")
    private String placeId;

    @NotBlank(message="반드시 이름을 지정해주세요!")
    private String placeName;
}
