package com.creswave.post.dto;

import com.creswave.comment.dto.CommentDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotNull(message = "title must be provided")
    private String title;

    @NotNull(message = "content must be provided")
    private String content;

    private List<CommentDto> comments;

    private Long postId;

    private String postBy;
}
