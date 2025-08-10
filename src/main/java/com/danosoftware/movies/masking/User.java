package com.danosoftware.movies.masking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class User {

    private String user;

    @Sensitive
    private String password;
}
