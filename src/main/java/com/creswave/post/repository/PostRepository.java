package com.creswave.post.repository;

import com.creswave.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            "SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%',:keyword,'%'))"
    )
    public Page<Post> findAll(@Param("keyword") String keyword, Pageable pageable);
}
