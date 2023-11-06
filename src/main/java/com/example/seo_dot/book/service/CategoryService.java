package com.example.seo_dot.book.service;

import com.example.seo_dot.book.domain.Category;
import com.example.seo_dot.book.repository.CategoryRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.seo_dot.book.domain.QCategory.category;

@RequiredArgsConstructor
@Service
public class CategoryService {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<String, List<String>> categoryPathMap = new HashMap<>();

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void initializeCategoryPathMap() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Category> leafCategories = queryFactory
                .selectFrom(category)
                .where(category.categoryCode.notIn(
                        JPAExpressions.select(category.parentCategoryCode.categoryCode).from(category.parentCategoryCode)))
                .fetch();

        for (Category category : leafCategories) {
            String leafCategoryCode = category.getCategoryCode();
            List<String> categoryPath = buildCategoryPath(category);
            categoryPathMap.put(leafCategoryCode, categoryPath);
        }
    }
    private List<String> buildCategoryPath(Category category) {
        List<String> categoryPath = new ArrayList<>();

        while (category != null) {
            categoryPath.add(category.getCategoryName());
            category = category.getParentCategoryCode();
        }
        Collections.reverse(categoryPath);

        return categoryPath;
    }
    public Map<String, List<String>> getCategoryPathsMap() {
        return categoryPathMap;
    }
}
