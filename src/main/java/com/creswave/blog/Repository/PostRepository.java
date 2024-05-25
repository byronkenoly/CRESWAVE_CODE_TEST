package com.creswave.blog.Repository;

import com.creswave.blog.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%" +
            "OR p.content LIKE %?1%")
    public Page<Post> findAll(String keyword, Pageable pageable);
}
