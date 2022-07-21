package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AdminRestController {
	
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/api/principal")
	public ResponseEntity<User> getPrincipalInfo(Principal principal) {
	    return ResponseEntity.ok().body(userService.getUserByName(principal.getName()));
	}
	
	@GetMapping(value="/api")
	public ResponseEntity<List<User>> findAllUsers(Model model) {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}
	
    @GetMapping("/api/{id}")
    public ResponseEntity<User> findOneUser(@PathVariable long id) {

        return ResponseEntity.ok().body(userService.getUserById((int)id));
    }
    
	
	@PostMapping(value="/api")
	public void create(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
	}
	
	@DeleteMapping(value="/api/{id}")
	public void remove(@PathVariable("id") int id) {
		userService.remove(id);
	}
	
	
	@PutMapping(value="/api/{id}")
	public void update(@RequestBody User user, @PathVariable("id") int id) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.update(id, user);
	}
	
	
}