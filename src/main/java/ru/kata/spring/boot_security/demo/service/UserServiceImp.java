package ru.kata.spring.boot_security.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Component
@Service
public class UserServiceImp implements UserService{

	@Autowired
	UserRepository userRepos;
	
	@Autowired
	RoleRepository roleRepos;
	
	@PersistenceContext
	EntityManager em;
	
	//@Autowired
    //BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public Integer getIdByUsername(String username) {
		Integer id = userRepos.getIdByName(username);
		
		if (id == null) {
			 throw new UsernameNotFoundException("User not found");
		} 
		return id;
	}
	
	@Override
	public User getUserById(int id) {
		return userRepos.findUserById(id);
	}

	@Override
	@Transactional
	public void save(User user) {
		userRepos.save(user);		
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepos.findAll();
	}

	@Override
	@Transactional
	public void update(int id, User user) {
		User temp = em.find(User.class, id);
		temp.setName(user.getName());
		temp.setWallet(user.getWallet());
		em.merge(temp);
		
	}

	@Override
	@Transactional
	public void remove(int id) {
		em.remove(em.find(User.class, id));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepos.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s not found", username));
		}
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
				user.getAuthorities());
	}
}
