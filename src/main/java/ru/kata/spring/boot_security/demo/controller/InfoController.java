package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequiredArgsConstructor
public class InfoController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping(value="/info")
	public String userPage(Model model, Principal principal) {
		User user = userService.getUserByName(principal.getName());
		model.addAttribute("user", user);
		return "admin_info";
	}
	
	
	
	
	
	
}
