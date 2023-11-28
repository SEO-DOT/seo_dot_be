package com.example.seo_dot.payment.service;

import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.repository.OrderRepository;
import com.example.seo_dot.payment.model.PayRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public void saveOrder(Long userId, List<PayRequestDto> payRequestDtos) {

        PayRequestDto payRequestDto = payRequestDtos.get(0);

        Order order = Order.builder()
                .userId(userId)
                .orderUid(payRequestDto.getOrderNumber())
                .price((long) payRequestDto.getOrderPrice())
//                .phoneNumber(payRequestDto.getPhoneNumber())
//                .zipcode(payRequestDto.getZipcode())
//                .address(payRequestDto.getAddress())
//                .orderRequired(payRequestDto.getOrderRequired())
//                .orderStatus(PaymentStatus.SUCCESS.getStatus())
//                .paymentMethod(payRequestDto.getPaymentMethod())
                .build();
        orderRepository.save(order);

        for (PayRequestDto dto : payRequestDtos) {
            int curStock = bookRepository.findById(dto.getBookId()).orElseThrow().getStock();
            int orderStock = dto.getOrderCount();

            if (curStock - orderStock < 0) {
                throw new IllegalArgumentException("상품 재고가 부족합니다.");
            }

//            상품 재고 줄이는 메서드?
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
        }
    }
}