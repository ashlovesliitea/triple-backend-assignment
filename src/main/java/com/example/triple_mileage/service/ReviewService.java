package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.*;
import com.example.triple_mileage.domain.entity.*;
import com.example.triple_mileage.dto.review.ReviewDeleteDto;
import com.example.triple_mileage.dto.review.ReviewFindDto;
import com.example.triple_mileage.dto.review.ReviewModifyDto;
import com.example.triple_mileage.dto.review.ReviewSaveDto;
import com.example.triple_mileage.exception.AlreadyWroteReviewException;
import com.example.triple_mileage.exception.InvalidUserException;
import com.example.triple_mileage.repository.PhotoRepository;
import com.example.triple_mileage.repository.PlaceRepository;
import com.example.triple_mileage.repository.ReviewRepository;
import com.example.triple_mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final PhotoRepository photoRepository;
    private final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    /**
     * 리뷰 작성
     *
     * @throws AlreadyWroteReviewException
     */
    @Transactional
    public void saveReview(ReviewSaveDto reviewSaveDto) throws AlreadyWroteReviewException {

        UUID reviewId = UUID.fromString(reviewSaveDto.getReviewId());
        String content = reviewSaveDto.getContent();
        UUID userId = UUID.fromString(reviewSaveDto.getUserId());
        UUID placeId = UUID.fromString(reviewSaveDto.getPlaceId());
        List<UUID> attachedPhotos = new ArrayList<>();
        for (String id : reviewSaveDto.getAttachedPhotoIds()) {
            attachedPhotos.add(UUID.fromString(id));
        }

        Review review = new Review();

        //리뷰 id, 컨텐츠, 사진 set
        review.setReviewId(reviewId);
        review.setContent(reviewSaveDto.getContent());
        review.setDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        for (UUID photoId : attachedPhotos) {
            Photo photo = Photo.createPhoto(photoId, review);
            review.addPhoto(photo);
        }


        //포인트 setting-글, 사진 추가했는지 확인
        review.setContentPoint((content.length() > 0) ? 1 : 0);
        review.setPhotoPoint((!attachedPhotos.isEmpty()) ? 1 : 0);

        User user = userRepository.findUser(userId);
        Place place = placeRepository.findOne(placeId);

        //기존 리뷰 작성 이력 확인
        List<Review> reviewList = place.getReviewList();
        if (!reviewList.isEmpty()) {
            for (Review findReview : reviewList) {
                if (findReview.getUser().getUserId().equals(userId)) {
                    throw new AlreadyWroteReviewException("이미 리뷰를 작성한 적이 있습니다!");
                    //Controller로 넘겨서 ExceptionAdvice로 해결
                }
            }
        }

        //보너스 점수 확인 - place의 review 리스트가 비었는지 확인
        review.setBonusPoint((place.getReviewList().size() == 0) ? 1 : 0);
        review.setUser(user);
        review.setPlace(place);

        PointHistory pointHistory = PointHistory.createPointHistory(user, PointSituation.REVIEW_ADDED, review.totalPoint());
        user.addPointHistory(pointHistory);

        userRepository.save(user);
        reviewRepository.save(review);
    }

    /**
     * 리뷰 조회
     *
     * @param reviewId
     * @return
     */

    public Review findReview(String reviewId) {
        return reviewRepository.findOne(UUID.fromString(reviewId));
    }

    public ReviewFindDto findReviewById(String reviewId){
        Review review=findReview(reviewId);
        String placeName=review.getPlace().getPlaceName();
        String userName=review.getUser().getUserName();
        List<String> attachedPhotoIds=new ArrayList<>();
        for(Photo photo: review.getPhotos()){
            attachedPhotoIds.add(photo.getPhotoId().toString());
        }
        return new ReviewFindDto(placeName,
                userName,
                review.getContent(),
                attachedPhotoIds,
                review.getDate());
    }

    /**
     * 리뷰 수정 (내용 or 사진)
     */

    @Transactional
    public void modifyReview(ReviewModifyDto reviewModifyDto) throws InvalidUserException {


        UUID reviewId = UUID.fromString(reviewModifyDto.getReviewId());
        String content = reviewModifyDto.getContent();
        List<UUID> attachedPhotos = new ArrayList<>();
        for (String photoId : reviewModifyDto.getAttachedPhotoIds()) {
            attachedPhotos.add(UUID.fromString(photoId));
        }

        Review review = findReview(reviewModifyDto.getReviewId());
        if(!reviewModifyDto.getUserId().equals(review.getUser().getUserId().toString())){
            throw new InvalidUserException("올바르지 못한 유저의 접근입니다!");
        }
        User user = review.getUser();

        review.setDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        review.modifyContents(content);

        if (attachedPhotos.isEmpty() && review.getPhotoPoint() == 1 && !review.getPhotos().isEmpty()) {
            //기존에 사진이 있었고, 수정해서 사진을 모두 지우는 경우
            review.setPhotoPoint(0);
            review.getPhotos().clear();
            //포인트 증감내역 추가 (-1)
            PointHistory pointHistory = PointHistory.createPointHistory(user, PointSituation.REVIEW_MODIFIED, -1);
            user.addPointHistory(pointHistory);
        } else if (review.getPhotos().isEmpty() && review.getPhotoPoint() == 0 && !attachedPhotos.isEmpty()) {
            //기존에 사진이 없었고, 사진을 추가한 경우
            List<Photo> attachedPhotoList = new ArrayList<>();
            //logger.info("비지 않음");
            for (UUID photoId : attachedPhotos) {
                Photo photo = Photo.createPhoto(photoId, review);
                attachedPhotoList.add(photo);
            }
            //기존에 사진이 없었던 경우는 사진 포인트 설정
            review.getPhotos().addAll(attachedPhotoList);
            review.setPhotoPoint(1);
            //포인트 증감내역 추가 (+1)
            PointHistory pointHistory = PointHistory.createPointHistory(user, PointSituation.REVIEW_MODIFIED, 1);
            user.addPointHistory(pointHistory);

        } else if (!attachedPhotos.isEmpty() && !review.getPhotos().isEmpty()) {
            //사진이 변경된 경우-포인트 변화는 없음
            //기존 사진에서 추가된 사진 & 기존에 있었는데 사라진 사진 check

            List<Photo> existingPhotos = review.getPhotos();

            //제거할 사진 확인
            for (Iterator<Photo> it = existingPhotos.listIterator(); it.hasNext(); ) {
                Photo photo = it.next();
                //새로운 첨부에도 존재하는지 확인
                Boolean curExistCheck = false;
                for (Iterator<UUID> it2 = attachedPhotos.listIterator(); it2.hasNext(); ) {
                    UUID attachedPhotoId = it2.next();
                    if (photo.getPhotoId().equals(attachedPhotoId)) {
                        //이미 존재하면 추후 추가할 사진에서 제외
                        logger.info("둘 다 존재하는 사진:{}", attachedPhotoId);
                        it2.remove();
                        curExistCheck = true;
                        break;
                    }
                }
                if (curExistCheck == false) {
                    //사진이 사라졌다면 기존 사진 리스트에서 remove
                    logger.info("삭제될 사진 id:{}", photo.getPhotoId().toString());
                    it.remove();
                }
            }

            for (UUID photoId : attachedPhotos) {
                logger.info("추가된 사진 id:{}", photoId);
                Photo photo = Photo.createPhoto(photoId, review);
                review.getPhotos().add(photo);

            }

        }
        userRepository.save(user);
        reviewRepository.save(review);

    }

    /**
     * 리뷰 삭제

     */
    @Transactional
    public void deleteReview(ReviewDeleteDto reviewDeleteDto) throws InvalidUserException {

        UUID reviewId = UUID.fromString(reviewDeleteDto.getReviewId());
        Review review = reviewRepository.findOne(reviewId);

        if(!reviewDeleteDto.getUserId().equals(review.getUser().getUserId().toString())){
            throw new InvalidUserException("올바르지 못한 유저의 접근입니다!");
        }

        //포인트 회수
        int totalPoint = review.totalPoint();
        User user = review.getUser();
        Place place = review.getPlace();


        PointHistory pointHistory = PointHistory.createPointHistory(user, PointSituation.REVIEW_DELETED, totalPoint * (-1));
        user.addPointHistory(pointHistory);

        review.getPhotos().clear(); //자식 엔티티인 사진 제거
        user.getReviewList().remove(review); //부모와의 관계 끊어줌
        place.getReviewList().remove(review);

        userRepository.save(user);
        reviewRepository.remove(review);
    }


}

