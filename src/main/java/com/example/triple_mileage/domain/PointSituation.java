package com.example.triple_mileage.domain;

import lombok.Getter;

public enum PointSituation {
    REVIEW_ADDED("REVIEW_ADDED"), REVIEW_MODIFIED("REVIEW_MODIFIED"), REVIEW_DELETED("REVIEW_DELETED"); //리뷰 작성시 포인트를 깎고, 올리는 상황 나열

    @Getter
    private final String value;

    PointSituation(String value) {
        this.value=value;
    }
}
