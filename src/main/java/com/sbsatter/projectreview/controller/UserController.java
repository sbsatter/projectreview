package com.sbsatter.projectreview.controller;

import com.sbsatter.projectreview.model.User;
import com.sbsatter.projectreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbsatter on 9/18/18.
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/find")
	public String findUserPageDisplay() {
		return "user/user";
	}
	
	
	@PostMapping("/find")
	public String findUserByusername(@ModelAttribute("username") String username, Model model) {
		User user = userService.getUser(username);
		if (user != null) {
			List<User> users = new ArrayList<>();
			users.add(user);
			model.addAttribute("users", users);
		}
		return "user/all";
	}
	
	
	@GetMapping("/add")
	public String addUserPage(Model model) {
		List<String> roles = new ArrayList<>();
		roles.add("USER");
		roles.add("ADMIN");
		model.addAttribute("roles", roles);
		return "user/add";
	}
	
	
	@PostMapping("/add")
	public String addUser(@ModelAttribute("user") User user, Model model, final RedirectAttributes redirectAttributes) {
		user = userService.addUser(user);
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/user/list";
	}
	
	@GetMapping("/list")
	public String findAllUsers(Model model) {
		List<User> list;
		list = userService.getAllUsers();
		model.addAttribute("users", list);
		return "user/all";
	}
}
