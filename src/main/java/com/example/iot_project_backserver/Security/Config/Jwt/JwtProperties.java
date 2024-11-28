package com.example.iot_project_backserver.Security.Config.Jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;
    private long accessTokenExpiration; // 억세스 토큰 만료 시간 (밀리초 단위)
    private long refreshTokenExpiration; // 리프레시 토큰 만료 시간 (밀리초 단위)
}
