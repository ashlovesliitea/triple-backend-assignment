package com.example.triple_mileage.domain;

import lombok.Getter;

public enum EventType {
    REVIEW("REVIEW");

    @Getter
    private final String value;

    EventType(String value) {
        this.value=value;
    }
}
