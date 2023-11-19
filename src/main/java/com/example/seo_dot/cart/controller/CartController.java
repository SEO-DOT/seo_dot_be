package com.example.seo_dot.cart.controller;

import com.example.seo_dot.cart.dto.CartRequestDTO;
import com.example.seo_dot.cart.dto.CartResponseDTO;
import com.example.seo_dot.cart.service.CartService;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class CartController {

    private final CartService cartService;

//    @GetMapping("/api/cart")
//    public ResponseEntity<?> getCart(Long userId) {
//        cartService.getCart(userId);
//        return null;
//    }

    @PostMapping("/api/cart")
    public ResponseEntity<MessageResponseDTO> addCart(@RequestBody CartRequestDTO requestDTO, User user) {
        MessageResponseDTO response = cartService.addCart(requestDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/cart/size")
    public ResponseEntity<CartResponseDTO> getCartSize(Long userId) {
        CartResponseDTO response = cartService.getCartSize(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
