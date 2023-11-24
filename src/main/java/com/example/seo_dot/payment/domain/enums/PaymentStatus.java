package com.example.seo_dot.payment.domain.enums;

public enum PaymentStatus {
    SUCCESS("결제완료"),
    REFUND("환불완료"),
    WAITING_FOR_PAYMENT("결제대기");
    private String status;

    public String getStatus() {
        return status;
    }

    PaymentStatus(String status) {
        this.status = status;
    }
}