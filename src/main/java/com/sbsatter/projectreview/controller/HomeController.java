package com.sbsatter.projectreview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by sbsatter on 9/18/18.
 */
@Controller
public class HomeController {
	
	@GetMapping("/")
//	@ResponseBody // we only use @ResponseBody when to send REST responses. SO it needs to be disabled here.
	public String landingPage() {
		return "landing_page";
	}
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard/dashboard";
	}
}
