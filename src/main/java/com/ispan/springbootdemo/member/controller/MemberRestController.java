package com.ispan.springbootdemo.member.controller;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.springbootdemo.member.dao.MemberRepository;
import com.ispan.springbootdemo.member.model.Member;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
public class MemberRestController {
    
    private MemberRepository memberRepository;
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    public void LoginController(MemberRepository memberRepository,RedisTemplate<String, String> redisTemplate) {
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
    }
    
	
  //*****驗證帳密*****
    @PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Member member) {
		// 從資料庫中尋找使用者
		Member dbUser = memberRepository.findByAccount(member.getAccount());

		if (dbUser != null && dbUser.getPassword().equals(member.getPassword())) {
			// 帳號密碼一致，回傳Access Token
			String accessToken = generateAccessToken(member.getAccount());
			return ResponseEntity.ok(accessToken);
		} else {
			// 帳號密碼不一致，回傳錯誤訊息
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
		}
	}
    
    
  //*****新增AccessToken並存到Redis*****
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

        // 存儲到Redis中
        String redisKey = "access_token:" + username;
        redisTemplate.opsForValue().set(redisKey, accessToken, expirationTime, TimeUnit.MILLISECONDS);

        return accessToken;
    }
    
  

}
