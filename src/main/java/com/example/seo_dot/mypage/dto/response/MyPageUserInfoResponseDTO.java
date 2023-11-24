package com.example.seo_dot.mypage.dto.response;

import com.example.seo_dot.user.domain.User;
import lombok.Getter;

@Getter
public class MyPageUserInfoResponseDTO {

    private Long userId;

    private String profileImage;

    private String nickName;

    private int reviewCount;

    private int point;

    private MyPageUserInfoResponseDTO(Long userId, String profileImage, String nickName, int reviewCount, int point) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.reviewCount = reviewCount;
        this.point = point;
    }

    public static MyPageUserInfoResponseDTO createFromUser(User user, int reviewCount) {
        return new MyPageUserInfoResponseDTO(user.getId(), user.getProfileImage(), user.getNickname(), reviewCount, user.getPoint());
    }

    public static MyPageUserInfoResponseDTO createWithDefaultProfileImage(User user, int reviewCount, String profileImage) {
        return new MyPageUserInfoResponseDTO(user.getId(), profileImage, user.getNickname(), reviewCount, user.getPoint());
    }
}
