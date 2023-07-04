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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/logged-in")
    public String loggedInPage(@RequestParam("account") String account,
                               @RequestParam("accessToken") String accessToken,
                               Model model,RedirectAttributes redirectAttributes) {

        // 從Redis中獲取存儲的accessToken
        String storedAccessToken = redisTemplate.opsForValue().get("access_token:" + account);

        // 比對傳遞的accessToken和存儲的accessToken
        if (storedAccessToken == null || !storedAccessToken.equals(accessToken)) {
            // 若不一致，回傳失效訊息並轉向登入頁面
        	System.out.println("error\", \"已從其他地方登入");
        	redirectAttributes.addFlashAttribute("error", "已從其他地方登入");
            return "redirect:/";
//            String errorMessage = "已從其他地方登入";
//            String encodedErrorMessage = URLEncoder.encode(errorMessage, "UTF-8");
//            return "redirect:/?error=" + encodedErrorMessage;
        }

        // 將帳號和 Access Token 傳遞到 JSP 頁面以顯示登入資訊
        model.addAttribute("account", account);
        model.addAttribute("accessToken", accessToken);

        // 返回 JSP 頁面的名稱
        return "/member/logged-in";
    }
    
    //*****登出並刪除redisKey*****
    @GetMapping("/logout")
    public String logout(@RequestParam("account") String username) {
        System.out.println("Log Out account :" + username);
        // 從Redis中刪除或標記Access Token為無效
        String redisKey = "access_token:" + username;
        System.out.println("redisKey=" + redisTemplate.opsForValue().get(redisKey));

        redisTemplate.delete(redisKey); // 或者使用其他Redis指令進行標記
        System.out.println("Deleted redisKey=" + redisTemplate.opsForValue().get(redisKey));
        return "/member/logged-out";
    }
    
}
	