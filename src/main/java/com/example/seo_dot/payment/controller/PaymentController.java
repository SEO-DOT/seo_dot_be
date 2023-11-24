package com.example.seo_dot.payment.controller;

import com.example.seo_dot.payment.model.PayRequestDto;
import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.payment.service.PaymentService;
import com.example.seo_dot.payment.service.RefundService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j(topic = "elk")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {
    private final PaymentService paymentService;
    private final RefundService refundService;

    private IamportClient iamportClient;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> paymentComplete(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PayRequestDto payRequestDto) throws IOException {
        try {
            Long userId = userDetails.getUser().getId();
            paymentService.saveOrder(userId, payRequestDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            String token = refundService.getToken(apiKey, secretKey);
            refundService.refundRequest(token, payRequestDto.getMerchantId(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/payment/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());
        return payment;
    }
}
