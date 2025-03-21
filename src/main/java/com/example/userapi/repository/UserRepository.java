package com.example.userapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByRole(String role);

	Optional<User> findBySsn(String ssn);
	
}
