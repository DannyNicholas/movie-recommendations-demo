package com.danosoftware.movies.masking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
//@SpringBootTest(classes = {LoggerMaskingAspect.class})
@SpringBootTest//(classes = {MongoDatabaseMovieRepository.class, RecommendationConfig.class})
//@TestPropertySource(properties = "movies.service.database.initialise=false")
//@ActiveProfiles("mongo")
public class MaskingTest {

    @Builder
    @AllArgsConstructor
    @ToString
    static class User {
        private String user;

        @Sensitive
        private String password;
    }

    @Test
    public void test() {
        User user = User.builder()
                .user("public user")
                .password("secret password")
                .build();

        log.info("User: {}", user);
    }
}
