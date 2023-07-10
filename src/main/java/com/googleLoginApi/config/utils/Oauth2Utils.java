package com.googleLoginApi.config.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix="oauth2.google")
public class Oauth2Utils {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String authUrl;
    private final String scopes;
    private final String loginUrl;

    public String googleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("redirect_uri", getRedirectUri());
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

        String paramString = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return loginUrl + "/o/oauth2/v2/auth?" + paramString;
    }

    public String getScopeUrl() {
        // scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함
        return scopes.replaceAll(",", "%20");
    }
}
