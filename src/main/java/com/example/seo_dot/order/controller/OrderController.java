package com.example.seo_dot.order.controller;

import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.order.domain.dto.request.OrderRequestDto;
import com.example.seo_dot.order.domain.dto.request.OrderUpdateDto;
import com.example.seo_dot.order.domain.dto.response.OrderResponseDto;
import com.example.seo_dot.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody OrderRequestDto requestDto) {
        log.info("RequestDto ={}", requestDto);
        OrderResponseDto responseDto = orderService.createOrder(userDetails.getUser(), requestDto.getCartIdList());
        log.info("ResponseDto ={}", responseDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PageableDefault(
                                               sort = "createdAt",
                                               direction = Sort.Direction.DESC
                                       ) Pageable pageable) {
        Slice<OrderResponseDto> orders = orderService.getOrders(userDetails.getUser(), pageable);
        return ResponseEntity.ok(orders.getContent());
    }

    @GetMapping("/orders/{orderNum}")
    public ResponseEntity<?> getOrderDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable String orderNum) {
        orderService.getOrderDetails(userDetails.getUser(), orderNum);
        return null;
    }

    @PatchMapping("/orders/{orderNum}")
    public ResponseEntity<?> updateOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderUpdateDto requestDto,
            @PathVariable String orderNum) {

//        ResponseOrderDto responseDto = orderService.updateOrder(userDetails.getUser(), requestDto, orderNum);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/orders/{orderNum}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderNum) {
        return null;
    }
}
