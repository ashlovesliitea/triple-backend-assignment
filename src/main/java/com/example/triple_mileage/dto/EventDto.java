package com.example.triple_mileage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventDto {
    @NotBlank(message = "시행할 이벤트 타입을 추가해주세요!")
    private String type;

    @NotBlank(message = "리뷰 관리시 수행할 행동을 지정해주세요!")
    private String action;

    @NotBlank(message="리뷰 아이디는 반드시 포함되어야 합니다.")
    private String reviewId;

    @NotBlank(message="글은 반드시 한자 이상 적어야 합니다.")
    private String content;

    private List<String> attachedPhotoIds;

    @NotBlank(message="유저 아이디는 반드시 포함되어야 합니다.")
    private String userId;

    @NotBlank(message="장소 아이디는 반드시 포함되어야 합니다.")
    private String placeId;
}
