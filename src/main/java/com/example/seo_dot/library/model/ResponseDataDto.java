package com.example.seo_dot.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseDataDto {

    private HttpStatus status;
    private String message;


    public static ResponseDataDto ok() {
        return ResponseDataDto.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();
    }

    public static ResponseDataDto fail(HttpStatus status, String message) {
        return ResponseDataDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}