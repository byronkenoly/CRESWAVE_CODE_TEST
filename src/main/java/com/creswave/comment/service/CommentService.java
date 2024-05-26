package com.creswave.comment.service;

import com.creswave.auth.entities.Role;
import com.creswave.auth.entities.User;
import com.creswave.auth.service.UserService;
import com.creswave.comment.dto.CommentDto;
import com.creswave.comment.entity.Comment;
import com.creswave.comment.repository.CommentRepository;
import com.creswave.infrastructure.exceptions.ResourceNotFoundException;
import com.creswave.infrastructure.exceptions.UnauthorizedException;
import com.creswave.post.entity.Post;
import com.creswave.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    @Transactional
    public void saveComment(CommentDto commentDto, String currentUserName){
        User user = userService.findByEmail(currentUserName);
        Post post = postService.getPost(commentDto.getPostId());

        var comment = Comment
                .builder()
                .content(commentDto.getContent())
                .post(post)
                .user(user)
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(
            Long id,
            CommentDto commentDto,
            String currentUserName
    ){
        Comment comment = getComment(id);

        checkOwnershipComment(comment, currentUserName);

        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);
    }

    private void checkOwnershipComment(Comment comment, String currentUserName){
        User currentlyLoggedInUser = userService.findByEmail(currentUserName);

        if (
                !comment.getUser().getUsername().equals(currentUserName)
                && !currentlyLoggedInUser.getRole().equals(Role.ADMIN)
        ) {
            throw new UnauthorizedException("You are not allowed to update/delete this comment");
        }
    }

    public Comment getComment(Long id){
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("comment with id: %d not found", id))
        );
    }

    @Transactional
    public void deleteComment(Long id, String currentUserName){
        Comment comment = getComment(id);

        checkOwnershipComment(comment, currentUserName);

        this.commentRepository.delete(comment);
    }
}
