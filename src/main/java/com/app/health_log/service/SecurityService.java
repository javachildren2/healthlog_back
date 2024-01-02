package com.app.health_log.service;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.Signature;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;


@Service
public class SecurityService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    //로그인 서비스 던질때 같이
    public String createToken(String subject, long expirationTime) {
        if (expirationTime <= 0) {
            throw new RuntimeException("만료 시간은 0보다 커야 합니다.");
        }
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new RuntimeException("JWT 서명 키가 올바르게 설정되지 않았습니다.");
        }

               return Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }



    public Key generateSecretKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return Keys.hmacShaKeyFor(apiKeySecretBytes);
    }

}
