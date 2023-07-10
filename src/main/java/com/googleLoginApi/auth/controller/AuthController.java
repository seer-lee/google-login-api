package com.googleLoginApi.auth.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.googleLoginApi.auth.dto.GoogleAuthDto;
import com.googleLoginApi.auth.dto.GoogleAuthReq;
import com.googleLoginApi.auth.dto.GoogleAuthRes;
import com.googleLoginApi.config.utils.Oauth2Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {
    private final Oauth2Utils oauth2Utils;

    @GetMapping("/login")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        String authUrl = oauth2Utils.googleInitUrl();
        URI redirectUri;
        try {
            redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }
    @GetMapping(value = "/login/oauth2/code/google")
    public ResponseEntity<GoogleAuthDto> redirectGoogleLogin(
        @RequestParam(value = "code") String authCode
    ) {
        // HTTP 통신을 위해 RestTemplate 활용
        RestTemplate restTemplate = new RestTemplate();
        GoogleAuthReq requestParams = GoogleAuthReq.builder()
            .clientId(oauth2Utils.getClientId())
            .clientSecret(oauth2Utils.getClientSecret())
            .code(authCode)
            .redirectUri(oauth2Utils.getRedirectUri())
            .grantType("authorization_code")
            .build();

        try {
            // Http Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GoogleAuthReq> httpRequestEntity = new HttpEntity<>(requestParams, headers);
            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(oauth2Utils.getAuthUrl() + "/token", httpRequestEntity, String.class);

            // ObjectMapper를 통해 String to Object로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
            GoogleAuthRes googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<>() {
            });

            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
            String jwtToken = googleLoginResponse.getIdToken();

            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
            String requestUrl = UriComponentsBuilder.fromHttpUrl(oauth2Utils.getAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

            String resultJson = restTemplate.getForObject(requestUrl, String.class);

            if(resultJson != null) {
                GoogleAuthDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<>() {
                });

                return ResponseEntity.ok().body(userInfoDto);
            }
            else {
                throw new Exception("Google OAuth failed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }
}