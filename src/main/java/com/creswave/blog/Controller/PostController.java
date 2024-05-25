package com.creswave.blog.Controller;

import com.creswave.blog.DTO.CommentDto;
import com.creswave.blog.DTO.PostDto;
import com.creswave.blog.Entity.Post;
import com.creswave.blog.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> viewPosts(){
        return ResponseEntity.ok(postService.getAllPost());
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.savePost(postDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> viewPost(@PathVariable (value = "id") Long no){
        return ResponseEntity.ok(postService.getPost(no));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable (value = "id") Long no, @RequestBody PostDto postDto) throws Exception {
        postService.updatePost(no, postDto);
        return ResponseEntity.ok("post updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable (value = "id") Long no) throws Exception {
        postService.deletePost(no);
        return ResponseEntity.ok("post deleted");
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable (value = "id") Long no, @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(postService.saveComment(no, commentDto));
    }

    @PatchMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable (value = "postId") Long no, @RequestBody CommentDto commentDto) throws Exception {
        postService.updateComment(no, commentDto);
        return ResponseEntity.ok("comment updated");
    }

    @DeleteMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId, @PathVariable (value = "commentId") Long commentId) throws Exception {
        postService.deleteComment(commentId);
        return ResponseEntity.ok("comment deleted");
    }

}
