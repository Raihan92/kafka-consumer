package com.progcreek.kafka.consumer.global;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reply<T> implements Serializable {

    private boolean success;

    private Integer code;

    private String message;

    private List<String> errors;

    private T payload;

    public Reply(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Reply(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Reply(boolean success, Integer code, String message, T payload) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public Reply(boolean success, Integer code, String message, List<String> errors, T payload) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.payload = payload;
    }

}
