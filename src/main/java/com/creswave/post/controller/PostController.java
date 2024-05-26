package com.creswave.post.controller;

import com.creswave.post.dto.PostDto;
import com.creswave.post.entity.Post;
import com.creswave.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    public ResponseEntity<?> getAllPost(
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", required = false, defaultValue = "title") String sortField,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
            @RequestParam(name = "search", required = false) String search
    ) {
        Page<PostDto> page = postService.getAllPost(
                pageNo,
                sortField,
                sortDir,
                search,
                pageSize
        ).map(Post::toDto);

        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestBody @Valid PostDto postDto,
            Principal principal
    ) {
        postService.savePost(postDto, principal.getName());
        return ResponseEntity.ok("post created");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> viewPost(
            @PathVariable(value = "postId") Long postId
    ) {
        PostDto postDto = postService.getPost(postId).toDto();
        return ResponseEntity.ok(postDto);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable(value = "postId") Long postId,
            @RequestBody PostDto postDto,
            Principal principal
    ) {
        postService.updatePost(postId, postDto, principal.getName());
        return ResponseEntity.ok("post updated");
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deletePost(
            @PathVariable(value = "postId") Long postId,
            Principal principal
    ) {
        postService.deletePost(postId, principal.getName());
        return ResponseEntity.ok("post deleted");
    }
}
