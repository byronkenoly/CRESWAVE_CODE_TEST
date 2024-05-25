package com.creswave.blog.Service;

import com.creswave.blog.DTO.CommentDto;
import com.creswave.blog.DTO.PostDto;
import com.creswave.blog.Entity.Comment;
import com.creswave.blog.Entity.Post;
import com.creswave.blog.Entity.User;
import com.creswave.blog.Repository.CommentRepository;
import com.creswave.blog.Repository.PostRepository;
import com.creswave.blog.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    private void checkOwnership(Post post) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (!post.getUser().getUsername().equals(currentUsername)) {
            throw new Exception("You are not allowed to update/delete this post");
        }
    }

    private void checkOwnershipComment(Comment comment) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new Exception("You are not allowed to update/delete this comment");
        }
    }

    public PostDto savePost(PostDto postDto){

        User user = userRepository.findByEmail(getCurrentUser()).orElseThrow();

        var post = Post
                .builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(user)
                .build();

        postRepository.save(post);

        return PostDto.builder().build();
    }

    public void updatePost(Long id, PostDto postDto) throws Exception {
        Post post = getPost(id);

        checkOwnership(post);

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public Post getPost(Long id){
        Optional<Post> optional = postRepository.findById(id);

        Post post = null;

        if (optional.isPresent()){
            post = optional.get();
        } else {
            throw new RuntimeException(id + " not found");
        }

        return post;
    }

    public void deletePost(Long id) throws Exception {
        Post post = getPost(id);

        checkOwnership(post);

        this.postRepository.delete(post);
    }

    public CommentDto saveComment(Long id, CommentDto commentDto){
        User user = userRepository.findByEmail(getCurrentUser()).orElseThrow();

        var comment = Comment
                .builder()
                .content(commentDto.getContent())
                .post(getPost(id))
                .user(user)
                .build();

        commentRepository.save(comment);

        return CommentDto.builder().build();
    }

    public void updateComment(Long id, CommentDto commentDto) throws Exception {
        Comment comment = getComment(id);

        checkOwnershipComment(comment);

        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);
    }

    public Comment getComment(Long id){
        Optional<Comment> optional = commentRepository.findById(id);

        Comment comment = null;

        if (optional.isPresent()){
            comment = optional.get();
        } else {
            throw new RuntimeException(id + " not found");
        }

        return comment;
    }

    public void deleteComment(Long id) throws Exception {
        Comment comment = getComment(id);

        checkOwnershipComment(comment);

        this.commentRepository.delete(comment);
    }
}
