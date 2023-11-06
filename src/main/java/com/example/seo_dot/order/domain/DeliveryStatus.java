package com.example.seo_dot.order.domain;

public enum DeliveryStatus {

    DELIVERY_PREPARING("배송 준비중"),
    DELIVERY_IN_PROGRESS("배송 중"),
    DELIVERY_COMPLETED("배송 완료");

    private String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
