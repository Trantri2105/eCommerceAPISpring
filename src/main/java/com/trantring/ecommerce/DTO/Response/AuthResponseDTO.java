package com.trantring.ecommerce.DTO.Response;

public class AuthResponseDTO {
    private int id;
    private String accessToken;
    private String tokenType;

    public AuthResponseDTO(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
