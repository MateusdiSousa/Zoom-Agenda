package com.example.demo.user.dto;

public record UserDto(
	String id,
	String name,
	String email,
	String password,
	Boolean active,
	Boolean admin 
) {}