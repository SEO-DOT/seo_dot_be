package com.example.seo_dot.mypage.dto.response;

import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.domain.OrderBookInfo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MyPageUserOrderListResponseDTO {

    private Long orderId;
    private String orderNum;
    private String orderStatus;
    private LocalDateTime orderDate;
    private int orderSize;
    private List<MyPageUserOrderItemResponseDTO> orderBookList = new ArrayList<>();

    public MyPageUserOrderListResponseDTO(Order order) {
        this.orderId = order.getId();
        this.orderNum = order.getOrderNum();
        this.orderStatus = order.getOrderStatus().getDescription();
        this.orderDate = order.getCreatedAt();
        this.orderSize = order.getOrderBooks().size();
        this.orderBookList = order.getOrderBooks().stream()
                .map(MyPageUserOrderItemResponseDTO::new).toList();
    }

    @Getter
    public static class MyPageUserOrderItemResponseDTO {
        private Long bookId;
        private String title;
        private String image;
        private String author;
        private String publisher;
        private String deliveryStatus;
        private int discountPrice;
        private int orderStock;

        public MyPageUserOrderItemResponseDTO(OrderBookInfo orderBookInfo) {
            this.bookId = orderBookInfo.getBook().getId();
            this.title = orderBookInfo.getBook().getTitle();
            this.image = orderBookInfo.getBook().getImage();
            this.author = orderBookInfo.getBook().getAuthor();
            this.publisher = orderBookInfo.getBook().getPublisher();
            this.deliveryStatus = orderBookInfo.getDeliveryStatus().getDescription();
            this.discountPrice = orderBookInfo.getBook().getDiscountPrice();
            this.orderStock = orderBookInfo.getQuantity();
        }
    }
}
