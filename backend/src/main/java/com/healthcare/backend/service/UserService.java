package com.healthcare.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.backend.model.User;
import com.healthcare.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User information cannot be null."
            );
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found with ID: " + userId
                        )
                );
    }

    public User updateUser(
            Long userId,
            User updatedUser) {

        User existingUser = getUserById(userId);

        existingUser.setName(updatedUser.getName());
        existingUser.setEmailId(updatedUser.getEmailId());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setStatus(updatedUser.getStatus());

        if (updatedUser.getPasswordHash() != null
                && !updatedUser.getPasswordHash().isBlank()) {

            existingUser.setPasswordHash(
                    updatedUser.getPasswordHash()
            );
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException(
                    "User not found with ID: " + userId
            );
        }

        userRepository.deleteById(userId);
    }
}