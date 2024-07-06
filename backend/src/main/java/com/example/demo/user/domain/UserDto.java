package com.example.demo.user.domain;

public record UserDto(
	String id,
	String name,
	String email,
	String password,
	Integer allow_level,
	Boolean active,
	Boolean admin 
) {}