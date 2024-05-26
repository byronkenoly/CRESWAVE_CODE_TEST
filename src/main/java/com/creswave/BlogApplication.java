package com.creswave;

import com.creswave.auth.dto.RegisterRequest;
import com.creswave.auth.service.AuthService;
import com.creswave.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

    private final AuthService authService;

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.isEmptyUsers()) {
            authService.register(
                    RegisterRequest.builder()
                            .name("admin")
                            .password("12345")
                            .email("admin@gmail.com")
                            .build()
			);
        }

    }
}
