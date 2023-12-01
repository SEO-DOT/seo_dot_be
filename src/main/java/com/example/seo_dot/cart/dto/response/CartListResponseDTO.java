package com.example.seo_dot.cart.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class CartListResponseDTO {

    private int cartSize;
    private List<CartListItem> cartList = new ArrayList<>();

    public CartListResponseDTO(int cartSize, List<CartListItem> cartList) {
        this.cartSize = cartSize;
        this.cartList = cartList;
    }

    @NoArgsConstructor
    @Getter
    public static class CartListItem {
        private Long cartId;
        private Long bookId;
        private String title;
        private String image;
        private int discountPrice;
        private int quantity;

        public CartListItem(Long cartId, Long bookId, String title, String image, int discountPrice, int quantity) {
            this.cartId = cartId;
            this.bookId = bookId;
            this.title = title;
            this.image = image;
            this.discountPrice = discountPrice;
            this.quantity = quantity;
        }
    }
}
