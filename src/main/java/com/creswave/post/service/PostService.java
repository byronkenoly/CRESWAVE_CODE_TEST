package com.creswave.post.service;

import com.creswave.auth.entities.Role;
import com.creswave.auth.entities.User;
import com.creswave.auth.service.UserService;
import com.creswave.infrastructure.exceptions.ResourceNotFoundException;
import com.creswave.infrastructure.exceptions.UnauthorizedException;
import com.creswave.post.dto.PostDto;
import com.creswave.post.entity.Post;
import com.creswave.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private void checkOwnership(Post post, String currentUsername){
        User currentlyLoggedInUser = userService.findByEmail(currentUsername);

        if (
                !post.getUser().getUsername().equals(currentUsername)
                && !currentlyLoggedInUser.getRole().equals(Role.ADMIN)
        ) {
            throw new UnauthorizedException("You are not allowed to update/delete this post");
        }
    }

    @Transactional
    public void savePost(PostDto postDto, String currentUserName){
        User user = userService.findByEmail(currentUserName);

        var post = Post
                .builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(user)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public void updatePost(
            Long id,
            PostDto postDto,
            String currentUserName
    ){
        Post post = getPost(id);

        checkOwnership(post, currentUserName);

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);
    }

    public Page<Post> getAllPost(
            int pageNo,
            String sortField,
            String sortDir,
            String search,
            int pageSize
    ){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (search != null){
            return postRepository.findAll(search, pageable);
        }

        return postRepository.findAll(pageable);
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("post with id: %d not found", id))
        );
    }

    @Transactional
    public void deletePost(Long id, String currentUserName){
        Post post = getPost(id);

        checkOwnership(post, currentUserName);

        this.postRepository.delete(post);
    }
}
