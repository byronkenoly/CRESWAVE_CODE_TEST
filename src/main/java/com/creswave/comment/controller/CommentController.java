package com.creswave.comment.controller;

import com.creswave.comment.dto.CommentDto;
import com.creswave.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(
            @RequestBody @Valid CommentDto commentDto,
            Principal principal
    ){
        commentService.saveComment(commentDto, principal.getName());
        return ResponseEntity.ok("comment created");
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable (value = "commentId") Long commentId,
            @RequestBody CommentDto commentDto,
            Principal principal
    ){
        commentService.updateComment(commentId, commentDto, principal.getName());
        return ResponseEntity.ok("comment updated");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable (value = "commentId") Long commentId,
            Principal principal
    ){
        commentService.deleteComment(commentId, principal.getName());
        return ResponseEntity.ok("comment deleted");
    }
}
