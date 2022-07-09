package com.example.triple_mileage.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatusCode {
    /**
     * 1000: Request 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    INVALID_REVIEW_ACTION(false,2001,"리뷰 관리시 할 수 없는 행위입니다."),
    INVALID_EVENT_TYPE(false,2002,"부적절한 이벤트 타입입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    DATA_NOT_FOUND(false,4002,"해당 데이터가 존재하지 않습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}
