package com.creswave.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull(message = "postId must be provided")
    private Long postId;

    @NotNull(message = "content must be provided")
    private String content;

    private Long commentId;

    private String commentBy;
}
