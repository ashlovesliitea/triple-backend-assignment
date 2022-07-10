package com.example.triple_mileage.service;

import com.example.triple_mileage.dto.PointDto;
import com.example.triple_mileage.repository.PointHistoryRepository;
import com.example.triple_mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PointService {

    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;

    public PointDto calculatePoint(String userIdStr){
        UUID userId= UUID.fromString(userIdStr);
        String userName= userRepository.findName(userId);
        int userPoint=pointHistoryRepository.calculatePoint(userId).intValue();
        return new PointDto(userName,userPoint);
    }
}
