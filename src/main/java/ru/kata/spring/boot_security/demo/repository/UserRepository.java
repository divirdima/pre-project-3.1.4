package ru.kata.spring.boot_security.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query ("SELECT p.id from User p where p.firstName = (:name)")
	Integer getIdByName(String name);
	User findUserById(int id);
	User findByFirstName(String firstName);
}
