package com.example.seo_dot.cart.controller;

import com.example.seo_dot.cart.dto.request.CartQuantityUpdateDTO;
import com.example.seo_dot.cart.dto.request.CartRequestDTO;
import com.example.seo_dot.cart.dto.response.CartListResponseDTO;
import com.example.seo_dot.cart.dto.response.CartQuantityResponseDTO;
import com.example.seo_dot.cart.dto.response.CartResponseDTO;
import com.example.seo_dot.cart.service.CartService;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<CartResponseDTO> addCart(@RequestBody CartRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("addCart");
        CartResponseDTO response = cartService.addCart(requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/cart")
    public ResponseEntity<CartListResponseDTO> getCartList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CartListResponseDTO response = cartService.getCartList(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/cart/size")
    public ResponseEntity<CartResponseDTO> getCartSize(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CartResponseDTO response = cartService.getCartSize(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/api/cart/{cartId}")
    public ResponseEntity<CartResponseDTO> deleteCartItem(@PathVariable Long cartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CartResponseDTO response = cartService.deleteCartItem(cartId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/cart/update-qty")
    public ResponseEntity<CartQuantityResponseDTO> updateCartItemQuantity(@RequestBody CartQuantityUpdateDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CartQuantityResponseDTO response = cartService.updateCartItemQuantity(requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
