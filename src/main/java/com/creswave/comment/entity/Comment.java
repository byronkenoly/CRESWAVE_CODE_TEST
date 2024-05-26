package com.creswave.comment.entity;

import com.creswave.auth.entities.User;
import com.creswave.comment.dto.CommentDto;
import com.creswave.post.entity.Post;
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
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public CommentDto toDto(){
        return CommentDto.builder()
                .commentId(this.id)
                .commentBy(user.getName())
                .content(this.getContent())
                .postId(this.post.getId())
                .build();
    }
}
