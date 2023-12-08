package com.example.seo_dot.order.domain.dto.response;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.order.domain.DeliveryStatus;
import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.domain.OrderBookInfo;
import com.example.seo_dot.order.domain.OrderStatus;
import com.example.seo_dot.user.domain.Address;
import com.example.seo_dot.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {

    private String orderNum;
    private String receiverName;
    private String receiverMemo;
    private String receiverPhone;
    private Address recipientAddress;
    private Long totalPrice;
    private Long totalDiscountPrice;
    private Long finalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    private List<OrderBookInfoDto> orderBooks;
    public OrderResponseDto(Order order) {
        this.orderNum = order.getOrderNum();
        this.createdAt = order.getCreatedAt();
        this.receiverMemo = order.getReceiverMemo();
        this.receiverName = order.getReceiverName();
        this.receiverPhone = order.getReceiverPhone();
        this.recipientAddress = order.getRecipientAddress();
        this.orderStatus = order.getOrderStatus();

        this.orderBooks = convertDto(order.getOrderBooks());
        this.totalPrice = order.getTotalPrice();
        this.totalDiscountPrice = order.getTotalDiscountPrice();
        this.finalPrice = order.getFinalPrice();
    }

    private List<OrderBookInfoDto> convertDto(List<OrderBookInfo> orderBookInfoList) {
        List<OrderBookInfoDto> dtos = new ArrayList<>();
        for (OrderBookInfo orderBookInfo : orderBookInfoList) {
            dtos.add(new OrderBookInfoDto(orderBookInfo));
        }
        return dtos;
    }
    @Getter
    private static class OrderBookInfoDto {
        private Long id;
        private String title;
        private String author;
        private String publisher;
        private String image;
        private Integer price;
        private DeliveryStatus deliveryStatus;
        private Integer quantity;

        public OrderBookInfoDto(OrderBookInfo orderBookInfo) {
            this.id = orderBookInfo.getId();
            this.title = orderBookInfo.getBook().getTitle();
            this.author = orderBookInfo.getBook().getAuthor();
            this.publisher = orderBookInfo.getBook().getPublisher();
            this.image = orderBookInfo.getBook().getImage();
            this.price = orderBookInfo.getBook().getPrice();
            this.deliveryStatus = orderBookInfo.getDeliveryStatus();
            this.quantity = orderBookInfo.getQuantity();
        }
    }
}
