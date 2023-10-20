package com.example.seo_dot.comment.infrastructure;

import com.example.seo_dot.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
