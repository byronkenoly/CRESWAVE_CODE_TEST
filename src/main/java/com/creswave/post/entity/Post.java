package com.creswave.post.entity;

import com.creswave.auth.entities.User;
import com.creswave.comment.entity.Comment;
import com.creswave.post.dto.PostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public PostDto toDto(){
        return PostDto.builder()
                .comments(this.comments.stream().map(Comment::toDto).collect(Collectors.toList()))
                .postId(this.id)
                .postBy(this.user.getName())
                .content(this.content)
                .title(this.title)
                .build();
    }
}
