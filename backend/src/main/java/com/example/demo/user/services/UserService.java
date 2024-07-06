package com.example.demo.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserDto;
import com.example.demo.user.domain.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<User> createUser(UserDto dto) {
		try {
			User user = new User(dto);
			user.setActive(true);
			var response = this.userRepository.save(user);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	public ResponseEntity<User> findOneByEmail(String email) {
		try {
			Optional<User> response = userRepository.findByEmail(email);
			if (response.isPresent()) {
				User user = response.get();
				return ResponseEntity.ok(user);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	public ResponseEntity<List<User>> findAll() {
		try {
			Optional<List<User>> response = userRepository.findAllByActiveTrue();
			if (response.isPresent()) {
				List<User> users = response.get();
				return ResponseEntity.ok(users);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Transactional
	public ResponseEntity<User> updateUser(UserDto dto) {
		try {
			Optional<User> response = userRepository.findByEmail(dto.email());
			if (response.isPresent()) {
				User user = response.get();
				user.setName(dto.name());
				user.setAllow_level(dto.allow_level());
				user.setAdmin(dto.admin());
				try {
					user.setEmail(dto.email());
				} catch (Exception e) {
					return ResponseEntity.badRequest().build();
				}
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@Transactional
	public ResponseEntity<String> deleteUser(String id) {
		Optional<User> response = userRepository.findById(id);
		if (response.isPresent()) {
			User user = response.get();
			user.setActive(false);
			return ResponseEntity.ok("Usuário deletado com sucesso");
		} 
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
