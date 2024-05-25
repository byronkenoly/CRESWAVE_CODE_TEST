package com.creswave.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
}
