package com.example.seo_dot.cart.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.cart.domain.Cart;
import com.example.seo_dot.cart.dto.request.CartQuantityUpdateDTO;
import com.example.seo_dot.cart.dto.request.CartRequestDTO;
import com.example.seo_dot.cart.dto.response.CartListResponseDTO;
import com.example.seo_dot.cart.dto.response.CartQuantityResponseDTO;
import com.example.seo_dot.cart.dto.response.CartResponseDTO;
import com.example.seo_dot.cart.repository.CartRepository;
import com.example.seo_dot.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public CartResponseDTO getCartSize(User user) {
        Integer cartSize = cartRepository.countByUserId(user.getId());
        return CartResponseDTO.createCartResponseStatus200(cartSize);
    }

    @Transactional
    public CartResponseDTO addCart(CartRequestDTO requestDTO, User user) {
        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException());
        Cart duplicatedCartItem = cartRepository.findByBookId(requestDTO.getBookId());
        Integer cartSize = cartRepository.countByUserId(user.getId());

        if (duplicatedCartItem != null) {
            duplicatedCartItem.increaseQuantity(requestDTO.getQuantity());
            return CartResponseDTO.createCustomMessage200(cartSize,"이미 담긴 상품의 수량이 업데이트되었습니다.");
        }

        Cart cart = Cart.createCart(user, book,requestDTO.getQuantity());
        cartRepository.save(cart);
        return CartResponseDTO.createCartResponseStatus201(cartSize+1);
    }

    @Transactional
    public CartResponseDTO deleteCartItem(Long cartId, User user) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException());
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException();
        }
        cartRepository.delete(cart);
        Integer cartSize = cartRepository.countByUserId(user.getId());
        return CartResponseDTO.createCartResponseStatus200(cartSize);
    }

    @Transactional
    public CartQuantityResponseDTO updateCartItemQuantity(CartQuantityUpdateDTO requestDTO, User user) {
        Cart cart = cartRepository.findById(requestDTO.getCartId())
                .orElseThrow(() -> new IllegalArgumentException());

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException();
        }

        cart.updateQuantity(requestDTO.getQuantity());
        return CartQuantityResponseDTO.create(cart.getQuantity());
    }

    public CartListResponseDTO getCartList(User user) {
        CartListResponseDTO result = cartRepository.findCartList(user.getId());
        return result;
    }
}