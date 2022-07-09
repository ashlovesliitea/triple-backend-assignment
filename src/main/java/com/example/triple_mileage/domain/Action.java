package com.example.triple_mileage.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Action {

    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");

    @Getter
    private final String value;

    Action(String value) {
        this.value=value;
    }

    @JsonCreator
    public static Action from(String value) {
        for (Action status : Action.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
