package com.example.triple_mileage.controller;

import com.example.triple_mileage.domain.Action;
import com.example.triple_mileage.domain.EventType;
import com.example.triple_mileage.dto.review.*;
import com.example.triple_mileage.exception.AlreadyWroteReviewException;
import com.example.triple_mileage.exception.InvalidUserException;
import com.example.triple_mileage.response.ResponseObj;
import com.example.triple_mileage.response.ResponseStatusCode;
import com.example.triple_mileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.triple_mileage.response.ResponseStatusCode.INVALID_EVENT_TYPE;


@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @ResponseBody
    @PostMapping("/events")
    public ResponseObj<String> manageReview(@Valid @RequestBody EventDto eventDTO) throws AlreadyWroteReviewException, InvalidUserException {

        if (eventDTO.getType().equals(EventType.REVIEW.getValue())) {
            String action = eventDTO.getAction();
            if (action.equals(Action.MOD.getValue())) {
                ReviewModifyDto reviewModifyDto = new ReviewModifyDto(eventDTO.getReviewId(),
                        eventDTO.getContent(),
                        eventDTO.getUserId(),
                        eventDTO.getAttachedPhotoIds());
                reviewService.modifyReview(reviewModifyDto);
                return new ResponseObj<>("리뷰 수정을 성공했습니다.");

            } else if (action.equals(Action.DELETE.getValue())) {
                ReviewDeleteDto reviewDeleteDto=new ReviewDeleteDto(eventDTO.getUserId(),
                        eventDTO.getReviewId());
                reviewService.deleteReview(reviewDeleteDto);
                return new ResponseObj<>("리뷰 삭제를 완료했습니다.");

            } else if (action.equals(Action.ADD.getValue())) {
                ReviewSaveDto reviewSaveDto = new ReviewSaveDto(eventDTO.getReviewId()
                        , eventDTO.getUserId()
                        , eventDTO.getPlaceId()
                        , eventDTO.getContent()
                        , eventDTO.getAttachedPhotoIds());
                reviewService.saveReview(reviewSaveDto);
                return new ResponseObj<>("리뷰 작성이 완료되었습니다.");
            }

            return new ResponseObj(ResponseStatusCode.INVALID_REVIEW_ACTION);
        }
        return new ResponseObj(INVALID_EVENT_TYPE);

    }

    @ResponseBody
    @GetMapping("/reviews/{reviewId}")
    public ResponseObj<ReviewFindDto> findReviewById(@PathVariable("reviewId") String reviewId){
        ReviewFindDto findReviewDto=reviewService.findReviewById(reviewId);
        return new ResponseObj<>(findReviewDto);
    }

}
