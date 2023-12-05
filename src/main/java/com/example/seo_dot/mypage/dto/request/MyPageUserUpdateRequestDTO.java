package com.example.seo_dot.mypage.dto.request;

import lombok.Getter;

@Getter
public class MyPageUserUpdateRequestDTO {

    private String userName;
    private String phoneNumber;

    private String zoneCode;
    private String streetAddress;
    private String detailAddress;
}
