package com.ispan.springbootdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
	@GetMapping("/")
	public String home(Model model){
		return "/member/login";
	}
}
