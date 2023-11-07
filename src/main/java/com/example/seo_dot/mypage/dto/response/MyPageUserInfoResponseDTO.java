package com.example.seo_dot.mypage.dto.response;

import com.example.seo_dot.user.domain.User;
import lombok.Getter;

@Getter
public class MyPageUserInfoResponseDTO {

    private Long userId;

    private String profileImage;

    private String nickName;

    private String address;

    private int reviewCount;

    private int point;

    public MyPageUserInfoResponseDTO(User user, int reviewCount) {
        this.userId = user.getId();
        this.profileImage = user.getProfileImage();
        this.nickName = user.getNickName();
        this.reviewCount = reviewCount;
        this.address = address;
        this.point = user.getPoint();
    }
}
