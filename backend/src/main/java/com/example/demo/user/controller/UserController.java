package com.example.demo.user.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserDto;
import com.example.demo.user.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		return userService.findAll();
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<User> getUser(@PathVariable String email){
		return userService.findOneByEmail(email);
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody UserDto dto){
		return userService.createUser(dto);
	}
	
	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody UserDto dto){
		return userService.updateUser(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {
		return userService.deleteUser(id);
	}
}
