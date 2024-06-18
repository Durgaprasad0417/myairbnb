package com.airbnb.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String type= "Bearer";
    private String token;
}
