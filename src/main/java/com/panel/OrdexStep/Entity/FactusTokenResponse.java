package com.panel.OrdexStep.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FactusTokenResponse {
    private String token_type;
    private Long expires_in;
    private String access_token;
    private String refresh_token;
    private LocalDateTime createdAt = LocalDateTime.now();
}