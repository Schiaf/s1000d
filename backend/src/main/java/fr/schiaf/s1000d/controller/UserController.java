package fr.schiaf.s1000d.controller;

import fr.schiaf.s1000d.model.User;
import fr.schiaf.s1000d.model.Role;
import fr.schiaf.s1000d.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getLogin() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }else{
            //encrypt the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null) {
            user.setRoles(Role.ROLE_USER); // Set default role if not provided
        }

        
    // Check if a user with the same login already exists
    if (userRepository.findByLogin(user.getLogin()).isPresent()) {
        return ResponseEntity.status(409).body(null); // Conflict: Login already exists
    }
    
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setLogin(user.getLogin());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}