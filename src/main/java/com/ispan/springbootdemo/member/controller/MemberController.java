package com.ispan.springbootdemo.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ispan.springbootdemo.member.dao.MemberRepository;
import com.ispan.springbootdemo.member.model.Member;

@Controller
public class MemberController {
    private MemberRepository memberRepository;
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    public void LoginController(MemberRepository memberRepository,RedisTemplate<String, String> redisTemplate) {
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
    }
	
	
    //*****登入後轉址*****
//    @GetMapping("/logged-in")
//    public String loggedInPage(@RequestParam("account") String account,
//                               @RequestParam("accessToken") String accessToken,
//                               Model model) {
//        // 在這裡處理登入成功後的邏輯
//        // 你可以使用 account 和 accessToken 進行相應的操作，例如從資料庫中檢索更多用戶資訊
//        
//        // 將帳號和 Access Token 傳遞到 JSP 頁面以顯示登入資訊
//        model.addAttribute("account", account);
//        model.addAttribute("accessToken", accessToken);
//        
//        // 返回 JSP 頁面的名稱
//        return "/member/logged-in";
//    }
    
    //*****登出*****
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("account") String username) {
        System.out.println("Log Out account :" + username);
        // 從Redis中刪除或標記Access Token為無效
        String redisKey = "access_token:" + username;
//        System.out.println("beforeDelete redisKey=" + redisTemplate.opsForValue().get(redisKey));

        redisTemplate.delete(redisKey); // 或者使用其他Redis指令進行標記
        System.out.println("Deleted redisKey=" + redisTemplate.opsForValue().get(redisKey));
        return ResponseEntity.ok("登出成功");
    }
    
}
	