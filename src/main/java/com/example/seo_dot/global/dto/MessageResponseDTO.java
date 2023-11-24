package com.example.seo_dot.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageResponseDTO {

    private int status;
    private String msg;

    private MessageResponseDTO(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static MessageResponseDTO createSuccessMessage201(){
        return new MessageResponseDTO(HttpStatus.CREATED.value(), "Success");
    }

    public static MessageResponseDTO createSuccessMessage200(){
        return new MessageResponseDTO(HttpStatus.OK.value(), "Success");
    }
}
