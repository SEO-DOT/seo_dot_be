package com.example.seo_dot.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String zoneCode;
    private String streetAddress;
    private String detailAddress;
}
