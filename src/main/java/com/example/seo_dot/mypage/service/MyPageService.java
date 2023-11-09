package com.example.seo_dot.mypage.service;

import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.global.image.ImageFolder;
import com.example.seo_dot.global.image.ImageUploader;
import com.example.seo_dot.mypage.dto.response.MyPageReviewResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserInfoResponseDTO;
import com.example.seo_dot.review.domain.Review;
import com.example.seo_dot.review.dto.request.ReviewPageParam;
import com.example.seo_dot.review.repository.ReviewLikeRepository;
import com.example.seo_dot.review.repository.ReviewRepository;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final ImageUploader imageUploader;

    public MyPageUserInfoResponseDTO getMyPageUserInfo(User user) {
        Integer reviewCount = reviewRepository.countByUserId(user.getId());
        MyPageUserInfoResponseDTO result = new MyPageUserInfoResponseDTO(user, reviewCount.intValue());
        return result;
    }

    public Slice<MyPageReviewResponseDTO> getMyPageReviews(ReviewPageParam pageParam, User user) {
        PageRequest pageRequest = PageRequest.of(pageParam.getPage(), pageParam.getPer());
        Slice<Review> reviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(pageRequest, user.getId());
        List<Long> reviewIdsByUserId = reviewLikeRepository.findReviewIdsByUserId(user.getId());

        List<MyPageReviewResponseDTO> result = reviews
                .stream()
                .map(review -> {
                    boolean liked = reviewIdsByUserId.contains(review.getId());
                    MyPageReviewResponseDTO response = new MyPageReviewResponseDTO(review, new MyPageReviewResponseDTO.MyPageReviewListBook(review.getBook()));
                    if (reviewIdsByUserId.contains(review.getId())) {
                        response.setLiked(liked);
                    }
                    return response;
                })
                .collect(Collectors.toList());

        return new SliceImpl<>(result,reviews.getPageable(),reviews.hasNext());
    }

    @Transactional
    public MessageResponseDTO updateProfile(MultipartFile multipartFile, String nickname, User user) {
        if (!nickname.equals(user.getNickname())) {
            boolean isExistsNickname = userRepository.existsByNickName(nickname);
            if (isExistsNickname) {
                throw new IllegalArgumentException();
            }
        }
        String profileImage = multipartFile.getOriginalFilename();
        if (!user.getPicture().equals(profileImage)) {
            imageUploader.deleteImage(user.getPicture());
            profileImage = imageUploader.storeImage(multipartFile, ImageFolder.PROFILE);
        }
        user.updateUserInfo(nickname, profileImage);
        return MessageResponseDTO.createSuccessMessage200();
    }
}
