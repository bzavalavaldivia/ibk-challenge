package com.ibk.identityserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String tokenType;
    private String accessToken;
    private Date expiresIn;
}
