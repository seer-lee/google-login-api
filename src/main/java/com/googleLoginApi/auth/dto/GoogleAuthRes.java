package com.googleLoginApi.auth.dto;

import lombok.Getter;

@Getter
public class GoogleAuthRes {
    private String accessToken; // 애플리케이션이 Google API 요청을 승인하기 위해 보내는 토큰
    private String expiresIn;   // Access Token의 남은 수명
    private String refreshToken;    // 새 액세스 토큰을 얻는 데 사용할 수 있는 토큰
    private String scope;
    private String tokenType;   // 반환된 토큰 유형(Bearer 고정)
    private String idToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
