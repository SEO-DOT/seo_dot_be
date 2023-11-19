package com.example.seo_dot.cart.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartResponseDTO {

    private int status;
    private String msg;
    private int cartSize;

    public CartResponseDTO(int status, String msg, int cartSize) {
        this.status = status;
        this.msg = msg;
        this.cartSize = cartSize;
    }

    public static CartResponseDTO createCartResponseStatus200(Integer cartSize) {
        return new CartResponseDTO(HttpStatus.OK.value(), "Success", cartSize.intValue());
    }
}
