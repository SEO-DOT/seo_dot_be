package com.example.seo_dot.cart.repository;

import com.example.seo_dot.cart.dto.response.CartListResponseDTO;

public interface QCartRepository {

    CartListResponseDTO findCartList(Long userId);

}
