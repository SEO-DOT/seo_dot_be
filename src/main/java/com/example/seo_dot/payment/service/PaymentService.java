package com.example.seo_dot.payment.service;

import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.repository.OrderRepository;
import com.example.seo_dot.payment.domain.enums.PaymentStatus;
import com.example.seo_dot.payment.model.PayRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    public void saveOrder(Long userId, PayRequestDto payRequestDto) {
        Order order = orderRepository.findById(payRequestDto.getOrderId()).orElseThrow();
        order.changePaymentStatus(PaymentStatus.SUCCESS);
    }

//    public void saveOrder(Long userId, PayRequestDto payRequestDto) {
//        OrderSaveDto saveDto = orderSaveDtos.get(0);
//        Orders orders = Orders.builder()
//                .userIdNo(userId)
//                .orderNumber(saveDto.getOrderNumber())
//                .receiverName(saveDto.getReceiverName())
//                .phoneNumber(saveDto.getPhoneNumber())
//                .zipcode(saveDto.getZipcode())
//                .address(saveDto.getAddress())
//                .orderRequired(saveDto.getOrderRequired())
//                .orderStatus(PayStatus.SUCCESS.getStatus())
//                .paymentMethod(saveDto.getPaymentMethod())
//                .build();
//        orderRepository.insertOrder(orders);
//
//        for (OrderSaveDto dto : orderSaveDtos) {
//            int curStock = productRepository.selectProductStock(dto.getProductId());
//            int orderStock = dto.getOrderCount();
//
//            if (curStock - orderStock < 0) {
//                throw new IllegalArgumentException("상품 재고가 부족합니다.");
//            }
//
//            OrderProduct orderProduct = OrderProduct.builder()
//                    .orderId(orders.getOrderId())
//                    .productId(dto.getProductId())
//                    .orderProductPrice(dto.getOrderPrice())
//                    .orderProductCount(dto.getOrderCount())
//                    .orderProductDiscount(dto.getOrderDiscount())
//                    .productImage(dto.getProductImage())
//                    .build();
//            orderProductRepository.insertOrderProduct(orderProduct);
//            productRepository.updateProductStock(dto.getProductId(), dto.getOrderCount());
//        }
//    }
}
