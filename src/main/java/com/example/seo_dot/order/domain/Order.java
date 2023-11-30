package com.example.seo_dot.order.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.cart.domain.Cart;
import com.example.seo_dot.global.entity.BaseEntity;
import com.example.seo_dot.order.domain.dto.request.OrderUpdateDto;
import com.example.seo_dot.user.domain.Address;
import com.example.seo_dot.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNum;

    // 배송지 정보
    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverPhone;

    @Column(nullable = true)
    private String receiverMemo;

    @Column(nullable = false)
    private Address recipientAddress;

    // 결제 금액
    private Long totalPrice;
    private Long totalDiscountPrice;
    private Long finalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderBookInfo> orderBooks = new ArrayList<>();;

    public Order(User user) {
        this.orderNum = createOrderNum();
        this.user = user;
        this.receiverName = user.getUsername();
        this.receiverPhone = user.getPhoneNumber();
        this.recipientAddress = user.getAddress();
        this.orderStatus = OrderStatus.PAYMENT_PENDING;
        this.totalPrice = 0L;
        this.totalDiscountPrice = 0L;
        this.finalPrice = 0L;
    }

    public static Order createOrder(User user) {
        return new Order(user);
    }
    public Order updateOrder(OrderUpdateDto requestDto) {
        this.receiverMemo = requestDto.getReceiverMemo();
        this.totalDiscountPrice = getDiscountPrices(requestDto.getReserveMoney());

        return this;
    }
    private String createOrderNum() {
        String uuid = UUID.randomUUID().toString();
        String replace = uuid.replaceAll("-", "");
        return replace.substring(0, 9);
    }

    public void getPaymentAmount() {
        for (OrderBookInfo orderBook : this.orderBooks) {
            Book book = orderBook.getBook();
            totalPrice += Long.valueOf(book.getPrice());
            totalDiscountPrice += Long.valueOf(book.getDiscountPrice());
        }
        this.finalPrice = totalPrice - totalDiscountPrice;
    }
    private Long getDiscountPrices(Long reserveMoney) {
        long totalDiscountPrice = 0;
        for (OrderBookInfo orderBook : this.orderBooks) {
            totalDiscountPrice += Long.valueOf(orderBook.getBook().getDiscountPrice());
        }
        return totalDiscountPrice - reserveMoney;
    }

    private Long getPrices() {
        long totalPrice = 0;
        for (OrderBookInfo orderBook : this.orderBooks) {
            totalPrice += Long.valueOf(orderBook.getBook().getPrice());
        }
        return totalPrice;
    }
}
