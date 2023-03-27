package com.fgieracki.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    @Override
    public String toString() {
        return "{" +
                "\"timestamp\":\"" + timestamp + '\"' +
                ", \"message\":\"" + message + '\"' +
                ", \"details\":\"" + details + '\"' +
                '}';
    }
}
