package com.example.seo_dot.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupInfoRequestDto {

    private String phoneNumber;
    private String nickname;
    private String postNumber;
    private String zoneCode;
    private String streetAddress;
    private String detailAddress;
    private String age;
    private String gender;
}