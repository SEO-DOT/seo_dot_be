package com.example.seo_dot.cart.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "carts")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;

    private Cart(User user, Book book, int quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }

    public static Cart createCart(User user, Book book, int quantity) {
        return new Cart(user, book, quantity);
    }

    public void updateQuantity(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    public void increaseQuantity(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException();
        }
        this.quantity += quantity;
    }
}
