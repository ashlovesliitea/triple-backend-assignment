package com.example.triple_mileage.request;

import com.example.triple_mileage.domain.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventDTO {
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
