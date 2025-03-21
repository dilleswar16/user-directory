package com.example.userapi.service;

import com.example.userapi.exception.UserNotFoundException;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
	private final RestTemplate restTemplate;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	@Value("${external.api.users.url}") // Fetch URL from application.properties
	private String externalApiUrl;

	public UserService(RestTemplate restTemplate, UserRepository userRepository, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	public List<User> loadUsers() {
		log.info("Fetching users from external API: {}", externalApiUrl);

		try {
			// Fetch API response
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(externalApiUrl, Map.class);
			Map<String, Object> responseBody = responseEntity.getBody();

			// Validate response
			if (responseBody == null || !responseBody.containsKey("users")) {
				log.warn("External API returned no users.");
				throw new IllegalStateException("Invalid API response, no users found.");
			}

			// Convert API response to List<User>
			List<User> users = objectMapper.convertValue(responseBody.get("users"),
					objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

			log.info("Fetched {} users from API. Saving to database...", users.size());
			log.debug("User data: {}", users);

			// Save users to database
			List<User> savedUsers = userRepository.saveAll(users);
			log.info("Successfully saved {} users to H2 database.", savedUsers.size());

			return savedUsers;

		} catch (HttpClientErrorException e) {
			log.error("HTTP Error while fetching users: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to fetch users from API. HTTP Error: " + e.getMessage());
		} catch (RestClientException e) {
			log.error("API call failed: {}", e.getMessage(), e);
			throw new RuntimeException("API request failed. Please try again later.");
		} catch (Exception e) {
			log.error("Unexpected error while loading users: {}", e.getMessage(), e);
			throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
		}
	}

	/**
	 * Get all users from the database
	 */
	public List<User> getAllUsers() {
		log.debug("Retrieving all users from H2 database...");
		List<User> users = userRepository.findAll();
		log.info("Exiting users: Found {} users: {}", users.size());
		return users;
	}

	/**
	 * Get users by role
	 */
	public List<User> getUsersByRole(String role) {
		log.info("Fetching users with role: {}", role);

		List<User> users = userRepository.findByRole(role);

		log.info("Exiting getUsersByRole: Found {} users with role: {}", users.size(), role);
		return users;
	}

	
	/**
     * Get all users sorted by age in ascending or descending order.
     */
    public List<User> getUsersSortedByAge(String order) {
        log.info("Fetching users sorted by age in {} order", order);

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("No users found in the database.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }

        List<User> sortedUsers = users.stream()
                .sorted(order.equalsIgnoreCase("desc")
                        ? Comparator.comparing(User::getBirthDate).reversed()
                        : Comparator.comparing(User::getBirthDate))
                .collect(Collectors.toList());

        log.info("Returning {} users sorted by age in {} order.", sortedUsers.size(), order);
        return sortedUsers;
    }

    /**
     * Find a specific user by ID.
     */
    public User getUserById(Long id) {
       
        log.info("Fetching user with ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    throw new UserNotFoundException("User not found with ID: " + id);
                });
    }

    /**
     * Find a specific user by SSN.
     */
    public User getUserBySSN(String ssn) {
        log.info("Fetching user with SSN: {}", ssn);

        Optional<User> userOptional = userRepository.findBySsn(ssn);

        if (userOptional.isEmpty()) {
            log.warn("User with SSN {} not found", ssn);
            throw new UserNotFoundException("User not found with SSN: " + ssn);
        }

        log.info("User with SSN {} found: {}", ssn, userOptional.get());
        return userOptional.get();
    }
	

	
}
