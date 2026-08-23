package com.sankalp.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {

    private String accessToken;
    private String refreshToken;
}
