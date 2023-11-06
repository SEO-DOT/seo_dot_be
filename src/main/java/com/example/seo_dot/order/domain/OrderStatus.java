package com.example.seo_dot.order.domain;

public enum OrderStatus {
    PAYMENT_COMPLETED("결제 완료됨"),
    PAYMENT_CANCELLED("결제 취소됨");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
