package ru.kata.spring.boot_security.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService extends UserDetailsService{
	public User getUserById(int id);
	public void remove(int id);
	public void save(User user);
	public void update(int id, User user);
	public List<User> getAllUsers();
	public Integer getIdByUsername(String username);
}
