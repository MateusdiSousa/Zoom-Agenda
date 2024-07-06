package com.example.demo.user.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>  {
	Optional<User> findByEmail(String email);
	Optional<List<User>> findAllByActiveTrue();
}
