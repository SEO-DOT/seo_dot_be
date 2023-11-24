package com.example.seo_dot.mypage.dto.response;

import com.example.seo_dot.user.domain.User;
import lombok.Getter;

@Getter
public class MyPageUserDetailResponseDTO {

    private String userName;
    private String phoneNumber;

    private String zoneCode;
    private String streetAddress;
    private String detailAddress;

    public MyPageUserDetailResponseDTO(User user) {
        this.userName = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.zoneCode = user.getAddress().getZoneCode();
        this.streetAddress = user.getAddress().getStreetAddress();
        this.detailAddress = user.getAddress().getDetailAddress();
    }
}
