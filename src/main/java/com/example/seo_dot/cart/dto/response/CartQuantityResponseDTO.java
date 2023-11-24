package com.example.seo_dot.cart.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartQuantityResponseDTO {

    private int status;
    private String msg;
    private int quantity;

    private CartQuantityResponseDTO(int status, String msg, int quantity) {
        this.status = status;
        this.msg = msg;
        this.quantity = quantity;
    }

    public static CartQuantityResponseDTO create(Integer quantity) {
        return new CartQuantityResponseDTO(HttpStatus.OK.value(), "Success", quantity.intValue());
    }
}
