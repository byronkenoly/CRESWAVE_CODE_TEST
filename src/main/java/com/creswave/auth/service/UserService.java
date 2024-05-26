package com.creswave.auth.service;

import com.creswave.auth.entities.User;
import com.creswave.auth.repository.UserRepository;
import com.creswave.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String currentUserName) {
        return userRepository.findByEmail(currentUserName).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
    }

    public boolean isEmptyUsers() {
        return userRepository.count() == 0;
    }
}
