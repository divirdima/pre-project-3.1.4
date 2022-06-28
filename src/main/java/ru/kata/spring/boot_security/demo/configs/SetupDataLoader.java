package ru.kata.spring.boot_security.demo.configs;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private final static int ROLE_ADMIN = 1;
    private final static int ROLE_USER = 2;

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
	
    @Autowired
    public SetupDataLoader(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Iterable<User> users = userService.getAllUsers();
		if (users.iterator().hasNext()) {
			return;
		} 
		
		Role adminRole = new Role (ROLE_ADMIN, "ROLE_ADMIN");
        Role userRole = new Role (ROLE_USER, "ROLE_USER");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User admin = new User();
        admin.setName("admin");
        admin.setWallet(64375);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        userService.save(admin);

        User user = new User();
        user.setName("user");
        user.setWallet(-2);
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(userRoles);
        userService.save(user);
	}
}
