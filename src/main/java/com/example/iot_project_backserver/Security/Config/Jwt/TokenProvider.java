package com.example.iot_project_backserver.Security.Config.Jwt;

import com.example.iot_project_backserver.Entity.User.app_user;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        System.out.println("Decoded key length: " + keyBytes.length); // 디버그 출력
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The JWT secret key must be at least 256 bits (32 bytes) long.");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // JWT 억세스 토큰 생성 메서드
    public String generateAccessToken(app_user user) {
        return generateToken(user, jwtProperties.getAccessTokenExpiration()); // 억세스 토큰 만료 시간 사용
    }

    // JWT 리프레시 토큰 생성 메서드
    public String generateRefreshToken(app_user user) {
        return generateToken(user, jwtProperties.getRefreshTokenExpiration()); // 리프레시 토큰 만료 시간 사용
    }

    // 공통 JWT 토큰 생성 메서드
    private String generateToken(app_user user, long expiredAt) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredAt);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입 : JWT
                .setIssuer(jwtProperties.getIssuer()) // 발행자 설정
                .setIssuedAt(now) // 현재 시간
                .setExpiration(expiryDate) // 만료 시간
                .setSubject(user.getUserid()) // 사용자 ID를 주제로 설정
                .claim("id", user.getUserid()) // 클레임: 유저 ID
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 서명: SecretKey와 함께 HS256 방식으로 암호화
                .compact();
    }


    // 리프레시 토큰을 검증하고 새로운 억세스 토큰 발급
    public String validateAndGenerateNewAccessToken(String refreshToken) {
        System.out.println("Validating refresh token...");
        // 리프레시 토큰 유효성 검증
        if (!validateToken(refreshToken)) {
            throw new JwtException("Invalid or expired refresh token");
        }
        // 리프레시 토큰에서 유저 ID 추출
        String userId = getUserId(refreshToken);
        // 유저 ID를 기반으로 새로운 억세스 토큰 생성
        app_user user = new app_user();
        user.setUserid(userId); // 유저 객체를 구성. 필요에 따라 DB에서 유저 정보를 가져올 수도 있음.
        String newAccessToken = generateAccessToken(user);
        System.out.println("New access token generated for user: " + userId);
        return newAccessToken;
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validateToken(String token) {
        System.out.println("Validating JWT Token...");
        System.out.println("Received Token: " + token);

        try {
            // 디버깅: 토큰 복호화 과정 시작
            System.out.println("Starting token parsing...");

            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // SecretKey로 복호화
                    .build()
                    .parseClaimsJws(token);

            // 디버깅: 토큰이 성공적으로 복호화됨
            System.out.println("Token is valid.");
            return true;
        } catch (JwtException e) {
            // 디버깅: JWTException 발생 시 로그 출력
            System.out.println("JWTException occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // 디버깅: IllegalArgumentException 발생 시 로그 출력
            System.out.println("IllegalArgumentException occurred: " + e.getMessage());
        } catch (Exception e) {
            // 디버깅: 예상치 못한 예외 처리
            System.out.println("Unexpected error during token validation: " + e.getMessage());
        }

        // 디버깅: 토큰이 유효하지 않은 경우
        System.out.println("Token is invalid.");
        return false;
    }



    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", Collections.singleton(authority));
    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public String getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", String.class);
    }

    // Claims 객체를 가져오는 메서드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // SecretKey로 복호화
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
