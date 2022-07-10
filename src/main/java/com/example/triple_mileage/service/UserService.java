package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.entity.Review;
import com.example.triple_mileage.domain.entity.User;
import com.example.triple_mileage.dto.user.UserSaveDto;
import com.example.triple_mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(UserSaveDto userSaveDto) {
        UUID userId = UUID.fromString(userSaveDto.getUserId());
        String userName = userSaveDto.getUserName();
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        userRepository.save(user);
    }

    @Transactional
    public void removeUser(String userIdStr) {
        UUID userId = UUID.fromString(userIdStr);
        User user = userRepository.findUser(userId);
        List<Review> reviewList = user.getReviewList();
        for (Review r : reviewList) {
            r.setUser(null); //연관관계 끊어줌(유저가 탈퇴해도 리뷰는 남겨둠)- Review의 userId=null로 세팅
        }
        //포인트 이력은 유저가 사라지면 자동으로 삭제됨
        userRepository.removeUser(user);
    }
}
