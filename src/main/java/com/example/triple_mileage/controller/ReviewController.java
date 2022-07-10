package com.example.triple_mileage.controller;

import com.example.triple_mileage.domain.Action;
import com.example.triple_mileage.domain.EventType;
import com.example.triple_mileage.exception.AlreadyWroteReviewException;
import com.example.triple_mileage.request.EventDTO;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.triple_mileage.response.ResponseStatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final Logger logger= LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("/event")
    public ResponseObj<String> manageReview(@Valid @RequestBody EventDTO eventDTO) throws AlreadyWroteReviewException {
        UUID reviewId=UUID.fromString(eventDTO.getReviewId());
        UUID userId=UUID.fromString(eventDTO.getUserId());
        UUID placeId=UUID.fromString(eventDTO.getPlaceId());
        List<UUID> attachedPhotoList=new ArrayList<>();
        for(String id:eventDTO.getAttachedPhotoIds()){
            attachedPhotoList.add(UUID.fromString(id));
        }

        //TODO: Enum Type Validation 추가
        if(eventDTO.getType().equals(EventType.REVIEW.getValue())){
            String action = eventDTO.getAction();
            if (action.equals(Action.MOD.getValue())) {
                reviewService.modifyReview(reviewId,
                        eventDTO.getContent(),
                        attachedPhotoList);
                return new ResponseObj<>("리뷰 수정을 성공했습니다.");
            } else if (action.equals(Action.DELETE.getValue())) {
                reviewService.deleteReview(reviewId);
                return new ResponseObj<>("리뷰 삭제를 완료했습니다.");

            } else if (action.equals(Action.ADD.getValue())) {
                reviewService.saveReview(reviewId,
                        userId,
                        placeId,
                        eventDTO.getContent(),
                        attachedPhotoList);
                return new ResponseObj<>("리뷰 작성이 완료되었습니다.");
            }

            return new ResponseObj(INVALID_REVIEW_ACTION);
        }
        return new ResponseObj(INVALID_EVENT_TYPE);

    }

}
