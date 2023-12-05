package com.example.seo_dot.order.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.cart.domain.Cart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_book_info")
@Entity
public class OrderBookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Integer quantity;

    private OrderBookInfo(Order order, Cart cart) {
        this.book = cart.getBook();
        this.quantity = cart.getQuantity();
        this.deliveryStatus = DeliveryStatus.DELIVERY_PREPARING;
        this.order = order;
    }

    public static OrderBookInfo createOrderBookInfo(Order order, Cart cart) {
        return new OrderBookInfo(order, cart);
    }
}
