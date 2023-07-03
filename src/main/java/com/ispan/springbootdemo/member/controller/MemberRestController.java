package com.ispan.springbootdemo.member.controller;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.springbootdemo.member.dao.MemberRepository;
import com.ispan.springbootdemo.member.model.Member;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
public class MemberRestController {
    
    private MemberRepository memberRepository;
    
    @Autowired
    public void LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        // 從資料庫中尋找使用者
    	Member dbUser = memberRepository.findByAccount(member.getAccount());
        
        if (dbUser != null && dbUser.getPassword().equals(member.getPassword())) {
            // 帳號密碼一致，回傳Access Token
            String accessToken = generateAccessToken(member.getAccount());
            return ResponseEntity.ok("ok");
        } else {
            // 帳號密碼不一致，回傳錯誤訊息
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
        }
    }
    
    private String generateAccessToken(String username) {
        // 定義 JWT 的相關參數
        String secretKey = "your-secret-key"; // 密鑰，請替換為你自己的密鑰
        long expirationTime = 86400000; // Token 過期時間，這裡設定為一天 (24 小時)

        // 生成 Access Token
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return accessToken;
    }
}
