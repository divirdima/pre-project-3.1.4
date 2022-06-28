package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(value="/users")
	public String show(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}
	
	@GetMapping(value="/users/new")
	public String addPage(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}
	
	@PostMapping(value="/new")
	public String create(@ModelAttribute("user") User user) {
		userService.save(user);
		return "redirect:/admin/users";
	}
	
	@PostMapping(value="/users/{id}/remove")
	public String remove(@PathVariable("id") int id) {
		userService.remove(id);
		return "redirect:/admin/users";
	}
	
	@GetMapping(value="/users/{id}/edit")
	public String update(Model model, @PathVariable("id") int id) {
		model.addAttribute("user", userService.getUserById(id));
		return "edit";
	}
	
	@PostMapping(value="/users/{id}")
	public String saveUpdate(@ModelAttribute("user") User user, @PathVariable("id") int id) {
		userService.update(id, user);
		return "redirect:/admin/users";
	}
	
	@GetMapping(value="/users/{id}")
	public String userPageForAdmin(Model model, @PathVariable("id") int id) {
		model.addAttribute("user", userService.getUserById(id));
		return "user";
	}

}