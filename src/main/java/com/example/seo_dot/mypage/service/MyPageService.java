package com.example.seo_dot.mypage.service;

import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.global.image.ImageFolder;
import com.example.seo_dot.global.image.ImageUploader;
import com.example.seo_dot.mypage.dto.request.MyPageOrderPageParam;
import com.example.seo_dot.mypage.dto.request.MyPageUserUpdateRequestDTO;
import com.example.seo_dot.mypage.dto.response.MyPageReviewResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserDetailResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserInfoResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserOrderListResponseDTO;
import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.repository.OrderRepository;
import com.example.seo_dot.comment.review.domain.Review;
import com.example.seo_dot.comment.review.dto.request.ReviewPageParam;
import com.example.seo_dot.comment.review.repository.ReviewLikeRepository;
import com.example.seo_dot.comment.review.repository.ReviewRepository;
import com.example.seo_dot.user.domain.Address;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
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
    private final OrderRepository orderRepository;

    public MyPageUserInfoResponseDTO getMyPageUserInfo(User user) {
        Integer reviewCount = reviewRepository.countByUserId(user.getId());

        if (user.getProfileImage() == null) {
            return MyPageUserInfoResponseDTO.createWithDefaultProfileImage(user,reviewCount, imageUploader.getDefaultProfileImage());
        }

        return MyPageUserInfoResponseDTO.createFromUser(user,reviewCount);
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

        return new SliceImpl<>(result, reviews.getPageable(), reviews.hasNext());
    }

    @Transactional
    public MessageResponseDTO updateProfile(MultipartFile multipartFile, String nickname, User user) {
        if (!nickname.equals(user.getNickname())) {
            boolean isExistsNickname = userRepository.existsByNickname(nickname);
            if (isExistsNickname) {
                throw new IllegalArgumentException();
            }
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            updateProfileImage(multipartFile, user);
        } else {
            deleteExistingProfileImage(user);
        }
        user.updateUserProfile(nickname, getProfileImagePath(multipartFile, user));
        userRepository.save(user);
        return MessageResponseDTO.createSuccessMessage200();
    }

    @Transactional
    public MessageResponseDTO updateUserInfo(MyPageUserUpdateRequestDTO requestDTO, User user) {
        Address address = new Address(requestDTO.getZoneCode(), requestDTO.getStreetAddress(), requestDTO.getDetailAddress());
        user.updateUserInfo(requestDTO.getUserName(), address, requestDTO.getPhoneNumber());
        userRepository.save(user);
        return MessageResponseDTO.createSuccessMessage200();
    }

    public MyPageUserDetailResponseDTO getUserDetail(User user) {
        MyPageUserDetailResponseDTO result = new MyPageUserDetailResponseDTO(user);
        return result;
    }

    public Slice<MyPageUserOrderListResponseDTO> getUserOrders(MyPageOrderPageParam pageParam, User user) {
        PageRequest pageRequest = PageRequest.of(pageParam.getPage(), pageParam.getPer(), Sort.Direction.DESC, "createdAt");
        Slice<Order> orders = orderRepository.findSliceByUser(user, pageRequest);
        Slice<MyPageUserOrderListResponseDTO> result = orders
                .map(MyPageUserOrderListResponseDTO::new);
        return result;
    }
    private void deleteExistingProfileImage(User user) {
        if (user.getProfileImage() != null) {
            imageUploader.deleteImage(user.getProfileImage());
        }
    }

    private void updateProfileImage(MultipartFile multipartFile, User user) {
        String profileImage = imageUploader.storeImage(multipartFile, ImageFolder.PROFILE);
        deleteExistingProfileImage(user);
        user.setProfileImage(profileImage);
    }

    private String getProfileImagePath(MultipartFile multipartFile, User user) {
        return (multipartFile != null && !multipartFile.isEmpty()) ? user.getProfileImage() : null;
    }
}
