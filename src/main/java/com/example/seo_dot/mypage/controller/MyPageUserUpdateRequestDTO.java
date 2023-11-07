package com.example.seo_dot.mypage.controller;

import lombok.Getter;

@Getter
public class MyPageUserUpdateRequestDTO {

    private String name;

    private String address;

    private String phoneNumber;

    private boolean isAdult;

    public MyPageUserUpdateRequestDTO(String name, String address, String phoneNumber, boolean isAdult) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isAdult = isAdult;
    }
}
