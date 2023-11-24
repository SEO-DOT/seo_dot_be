package com.example.seo_dot.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {
    private String zoneCode;
    private String streetAddress;
    private String detailAddress;

    public Address(String zoneCode, String streetAddress, String detailAddress) {
        this.zoneCode = zoneCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }
}
