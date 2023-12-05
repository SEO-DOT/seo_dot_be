package com.example.seo_dot.cart.dto.response;

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

    public static CartResponseDTO createCartResponseStatus201(Integer cartSize) {
        return new CartResponseDTO(HttpStatus.CREATED.value(), "Success", cartSize.intValue());
    }
    public static CartResponseDTO createCustomMessage200(Integer cartSize, String msg) {
        return new CartResponseDTO(HttpStatus.OK.value(), msg, cartSize.intValue());
    }
}
