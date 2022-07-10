package com.example.triple_mileage;

import com.example.triple_mileage.domain.entity.*;
import com.example.triple_mileage.dto.ReviewModifyDto;
import com.example.triple_mileage.dto.ReviewSaveDto;
import com.example.triple_mileage.exception.AlreadyWroteReviewException;
import com.example.triple_mileage.repository.PlaceRepository;
import com.example.triple_mileage.repository.PointHistoryRepository;
import com.example.triple_mileage.repository.UserRepository;
import com.example.triple_mileage.service.PointService;
import com.example.triple_mileage.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class ReviewTest {

    //해당 Test 실행시에는 시나리오상 전체 테스트를 순서대로 실행해야 함
    /*
      유저 생성->장소 생성->리뷰 생성->같은 장소에 또 리뷰 생성(예외 발생)
      ->리뷰사진 전체삭제(사진 점수 회수)->사진 없는 리뷰에 사진 추가(사진 점수 추가)->사진 변경->리뷰 삭제->포인트 총점 계산
     */
    @Autowired
    ReviewService reviewService;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Autowired
    PointService pointService;

    final String userIdStr="3ede0ef2-92b7-4817-a5f3-0c575361f745";
    final String placeIdStr="2e4baf1c-5acb-4efb-a1af-eddada31b00f";
    final String reviewIdStr="240a0658-dc5f-4878-9381-ebb7b2667772";

    Logger logger= LoggerFactory.getLogger(PlaceTest.class);


    @Test
    @Rollback(value = false)
    public void reviewA_userSave(){
        User user=new User();
        String userIdStr="3ede0ef2-92b7-4817-a5f3-0c575361f745";
        UUID userId=UUID.fromString(userIdStr);
        user.setUserId(userId);
        user.setUserName("트리플");
        userRepository.save(user);

        User findUser=userRepository.findUser(userId);
        logger.info("저장된 uuid:{}",findUser.getUserId().toString());

        Assert.assertEquals(userId,findUser.getUserId());
    }


    @Test
    @Rollback(value = false)
    public void reviewB_placeSave(){
        Place place=new Place();

        UUID placeId=UUID.fromString(placeIdStr);
        place.setPlaceId(placeId);
        place.setPlaceName("신라스테이 강남");
        placeRepository.save(place);

        Place findPlace=placeRepository.findOne(placeId);
        logger.info("저장된 uuid:{}",findPlace.getPlaceId().toString());

        Assert.assertEquals(placeId,findPlace.getPlaceId());
        Assert.assertEquals(0,place.getReviews().size());
    }

    @Test
    @Rollback(value = false)
    public void reviewC_reviewSave() throws AlreadyWroteReviewException {
        UUID reviewId=UUID.fromString(reviewIdStr);
        UUID placeId=UUID.fromString(placeIdStr);

        String content="좋아요!";
        List<String> attachedPhotos = getAttachedPhotos();



        reviewService.saveReview(new ReviewSaveDto(reviewIdStr,userIdStr,placeIdStr,content,attachedPhotos));

        Review findReview= reviewService.findReview(reviewIdStr);
        Place findPlace=placeRepository.findOne(placeId);
        int reviewCnt=findReview.getPlace().getReviews().size();

        Assert.assertEquals(1,reviewCnt);
        Assert.assertEquals(1,findPlace.getReviews().size());
        Assert.assertEquals(3, findReview.totalPoint());


    }

    //리뷰를 중복작성하려고 할 때
    @Test(expected=AlreadyWroteReviewException.class)
    @Rollback(value = false)
    public void reviewD_reviewDuplicateSave() throws AlreadyWroteReviewException {
        UUID reviewId=UUID.fromString(reviewIdStr);
        UUID placeId=UUID.fromString(placeIdStr);
        UUID userId=UUID.fromString(userIdStr);

        String content="좋아요!";
        List<String> attachedPhotos = getAttachedPhotos();

        reviewService.saveReview(new ReviewSaveDto(reviewIdStr,userIdStr,placeIdStr,content,attachedPhotos));

    }

    //기존에 있었는데 사진을 모두 삭제한 경우
    @Test
    @Rollback(value = false)
    public void reviewE_Modify1(){
        List<String> emptyPhotoList=new ArrayList<>();
        reviewService.modifyReview(new ReviewModifyDto(reviewIdStr,"좋았어요!",emptyPhotoList));

        Review modifiedReview=reviewService.findReview(reviewIdStr);
        Assert.assertEquals("좋았어요!",modifiedReview.getContent());
        Assert.assertEquals(0,modifiedReview.getPhotos().size());

    }

    //사진이 없었는데 추가한 경우
    @Test
    @Rollback(value = false)
    public void reviewF_Modify2(){

        List<String> addedPhotoList=getAttachedPhotos();
        reviewService.modifyReview(new ReviewModifyDto(reviewIdStr,"좋았어요!",addedPhotoList));

        Review modifiedReview=reviewService.findReview(reviewIdStr);
        Assert.assertEquals(2,modifiedReview.getPhotos().size());
        Assert.assertEquals(1,modifiedReview.getPhotoPoint());

    }


    //사진을 변경한 경우
    @Test
    @Rollback(value = false)
    public void reviewG_Modify3(){
        UUID reviewId=UUID.fromString(reviewIdStr);
        List<String> modifiedPhotoList=new ArrayList<>();
        String photoId1=UUID.randomUUID().toString();//기존에 없는 사진
        logger.info("추가된 id:{}",photoId1.toString());
        String photoId2="afb0cef2-851d-4a50-bb07-9cc15cbdc332";//기존에 있는 사진
        modifiedPhotoList.add(photoId1);
        modifiedPhotoList.add(photoId2);

        reviewService.modifyReview(new ReviewModifyDto(reviewIdStr,"좋았어요!",modifiedPhotoList));

        Review modifiedReview=reviewService.findReview(reviewIdStr);
        List<Photo> modifiedPhotos=modifiedReview.getPhotos();
        Assert.assertEquals(2,modifiedReview.getPhotos().size());
        Assert.assertEquals(true,modifiedPhotos.stream().anyMatch(p->p.getPhotoId().equals(UUID.fromString(photoId1))));
        Assert.assertEquals(true,modifiedPhotos.stream().anyMatch(p->p.getPhotoId().equals(UUID.fromString(photoId2))));

    }

    //리뷰 삭제
    @Test
    @Rollback(value = false)
    public void reviewH_DELETE(){
        reviewService.deleteReview(reviewIdStr);
        Place place= placeRepository.findOne(UUID.fromString(placeIdStr));
        User user=userRepository.findUser(UUID.fromString(userIdStr));

        //삭제가 DB에 커밋되기 이전이므로 엔티티에서 찾아야함.
        List<PointHistory> pointHistories=user.getPointHistories();
        PointHistory pointHistory=pointHistories.get(pointHistories.size()-1);

        Assert.assertEquals(-3,pointHistory.getChangedPoint());
        //장소와 유저와 리뷰의 연관관계 끊어졌는지 확인
        Assert.assertEquals(0,place.getReviews().size());
        Assert.assertEquals(0,user.getReviewList().size());

    }

    //포인트 총점 조회
    @Test
    public void reviewI_PointTest(){
        int point=pointService.calculatePoint(userIdStr).getUserTotalPoint();
        Assert.assertEquals(0,point);
    }

    private List<String> getAttachedPhotos() {
        String photoId1="e4d1a64e-a531-46de-88d0-ff0ed70c0bb8";
        String photoId2="afb0cef2-851d-4a50-bb07-9cc15cbdc332";
        List<String> attachedPhotos=new ArrayList<>();
        attachedPhotos.add(photoId1);
        attachedPhotos.add(photoId2);
        return attachedPhotos;
    }
}
