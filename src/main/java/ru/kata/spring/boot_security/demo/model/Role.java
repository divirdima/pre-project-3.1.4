package ru.kata.spring.boot_security.demo.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role  implements GrantedAuthority {
	private static final long serialVersionUID = -5733464313832340954L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	public Role() {
		
	}
	
	public Role(int id) {
		this.id = id;
	}
	
	public Role(String name) {
		this.name = name;
	}
	
	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}


	@Override
	public String getAuthority() {
		return getName();
	}
	
}
