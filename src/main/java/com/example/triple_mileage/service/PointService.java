package com.example.triple_mileage.service;

import com.example.triple_mileage.domain.PointHistory;
import com.example.triple_mileage.domain.User;
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

    public int calculatePoint(UUID userId){

        return pointHistoryRepository.calculatePoint(userId).intValue();
    }
}
