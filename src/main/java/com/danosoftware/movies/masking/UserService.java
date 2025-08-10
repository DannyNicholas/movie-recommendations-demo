package com.danosoftware.movies.masking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    User user = User.builder()
            .user("public user")
            .password("secret password")
            .build();

    public void log() {
        log.info("User: {}", user);
    }
}
