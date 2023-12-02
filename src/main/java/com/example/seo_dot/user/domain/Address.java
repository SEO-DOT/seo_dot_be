package com.example.seo_dot.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zoneCode;
    private String streetAddress;
    private String detailAddress;
}
