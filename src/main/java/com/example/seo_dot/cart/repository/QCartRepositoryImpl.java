package com.example.seo_dot.cart.repository;

import com.example.seo_dot.cart.dto.response.CartListResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.seo_dot.cart.domain.QCart.cart;

@RequiredArgsConstructor
@Repository
public class QCartRepositoryImpl implements QCartRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public CartListResponseDTO findCartList(Long userId) {
        Long cartSize = queryFactory.select(cart.count())
                .from(cart)
                .where(cart.user.id.eq(userId))
                .fetchOne();

        List<CartListResponseDTO.CartListItem> cartListItems = queryFactory
                .select(
                        Projections.constructor(
                                CartListResponseDTO.CartListItem.class,
                                cart.id,
                                cart.book.id,
                                cart.book.title,
                                cart.book.image,
                                cart.book.price.subtract(cart.book.discountRate.multiply(cart.book.price).divide(100)),
                                cart.quantity
                        )
                )
                .from(cart)
                .innerJoin(cart.book)
                .where(cart.user.id.eq(userId))
                .fetch();

        return new CartListResponseDTO(Math.toIntExact(cartSize), cartListItems);
    }
}
