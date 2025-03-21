package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Directory API", description = "Load user details into DB from external API, CRUD operations")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to load all users from API dataset to H2 database
     */
    @PostMapping("/load")
    @Operation(
    	    summary = "Fetch user data from an external API and store it in the database.",
    	    description = "Retrieves user data from a specified external API and saves the details into the database."
    	)
    public ResponseEntity<String> loadUsersIntoDatabase() {
        log.info("Received request to load all users into H2 database.");

        try {
            List<User> savedUsers = userService.loadUsers();
            log.info("Successfully loaded {} users into H2 database.", savedUsers.size());
            return ResponseEntity.ok("Successfully loaded " + savedUsers.size() + " users into the database.");
        } catch (Exception e) {
            log.error("Error while loading users into the database: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to load users. Error: " + e.getMessage());
        }
    }

    /**
     * Fetch all users from the database
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Fetches all users from the database")
    public ResponseEntity<?> getAllUsers() {
        log.info("Fetching all users from the database...");
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                log.warn("No users found in the database.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users available");
            }
            log.info("Successfully retrieved {} users.", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching users. Please try again later.");
        }
    }

    /**
     * Fetch users by role
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Fetches users by their role")
    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
        log.info("Fetching users with role: {}", role);
        try {
            List<User> users = userService.getUsersByRole(role);
            if (users.isEmpty()) {
                log.warn("No users found with role: {}", role);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users available for the given role");
            }
            log.info("Successfully retrieved {} users with role {}", users.size(), role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching users by role: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching users. Please try again later.");
        }
    }

    /**
     * Get users sorted by age
     */
    @GetMapping("/sorted")
    @Operation(summary = "Get users sorted by age", description = "Fetches users sorted by age in ascending or descending order")
    public ResponseEntity<List<User>> getUsersSortedByAge(@RequestParam(defaultValue = "asc") String order) {
        log.info("Received request to get users sorted by age in {} order", order);
        List<User> users = userService.getUsersSortedByAge(order);
        log.info("Successfully fetched {} users sorted by age in {} order", users.size(), order);
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID.
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches a user using their unique ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.debug("Received request to fetch user by ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Get user by SSN.
     */
    @GetMapping("/ssn/{ssn}")
    @Operation(summary = "Get user by SSN", description = "Fetches a user using their Social Security Number (SSN)")
    public ResponseEntity<User> getUserBySSN(@PathVariable String ssn) {
        log.debug("Received request to fetch user by SSN: {}", ssn);
        return ResponseEntity.ok(userService.getUserBySSN(ssn));
    }
}
