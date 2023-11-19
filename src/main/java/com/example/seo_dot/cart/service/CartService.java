package com.example.seo_dot.cart.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.cart.dto.CartRequestDTO;
import com.example.seo_dot.cart.domain.Cart;
import com.example.seo_dot.cart.dto.CartResponseDTO;
import com.example.seo_dot.cart.repository.CartRepository;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

//    public void getCart(Long userId) {
//        cartRepository.
//    }

    public CartResponseDTO getCartSize(Long userId) {
        Integer cartSize = cartRepository.countByUserId(userId);
        return CartResponseDTO.createCartResponseStatus200(cartSize);
    }


    @Transactional
    public MessageResponseDTO addCart(CartRequestDTO requestDTO, User user) {
        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException());
        Cart cart = new Cart(user, book,requestDTO.getQuantity());
        cartRepository.save(cart);

        return MessageResponseDTO.createSuccessMessage201();
    }
}
