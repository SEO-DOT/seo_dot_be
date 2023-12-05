package com.example.seo_dot.order.service;

import com.example.seo_dot.cart.domain.Cart;
import com.example.seo_dot.cart.repository.CartRepository;
import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.domain.OrderBookInfo;
import com.example.seo_dot.order.domain.dto.response.OrderResponseDto;
import com.example.seo_dot.order.repository.OrderBookInfoRepository;
import com.example.seo_dot.order.repository.OrderRepository;
import com.example.seo_dot.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderBookInfoRepository orderBookInfoRepository;
    private final CartRepository cartRepository;

    public Slice<OrderResponseDto> getOrders(User user, Pageable pageRequest) {
        Slice<OrderResponseDto> sliceByUser =
                orderRepository.findSliceByUser(user, pageRequest)
                        .map(OrderResponseDto::new);
        return sliceByUser;
    }

    public OrderResponseDto getOrderDetails(User user, String orderNum) {
        Order order = orderRepository.findByUserAndOrderNum(user, orderNum).orElse(null);
        return new OrderResponseDto(order);
    }

    private void validateOrderOwner(String orderId) {

    }

    @Transactional
    public OrderResponseDto createOrder(User user, List<Long> cartIdList) {
        Order order = Order.createOrder(user);
        orderRepository.save(order);

        for (Long cartId : cartIdList) {
            Cart findCart = cartRepository.findByIdAndUserId(cartId, user.getId());
            if (findCart != null) {
                createOrderBookInfo(order, findCart);
            }
        }
        log.info("orderOrderBookInfo ={}", order.getOrderBooks());
        order.getPaymentAmount();
        return new OrderResponseDto(order);
    }

    private void createOrderBookInfo(Order order, Cart cart) {
        OrderBookInfo orderBookInfo = OrderBookInfo.createOrderBookInfo(order, cart);
        orderBookInfoRepository.save(orderBookInfo);
        order.getOrderBooks().add(orderBookInfo);
    }
}
