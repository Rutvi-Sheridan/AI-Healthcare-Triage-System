package com.healthcare.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.backend.dto.AuthResponse;
import com.healthcare.backend.dto.LoginRequest;
import com.healthcare.backend.dto.RegisterRequest;
import com.healthcare.backend.model.Patient;
import com.healthcare.backend.model.Role;
import com.healthcare.backend.model.User;
import com.healthcare.backend.model.UserStatus;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PatientRepository patientRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse registerPatient(RegisterRequest request) {

        String normalizedEmail =
                request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmailId(normalizedEmail)) {
            throw new IllegalArgumentException(
                    "An account already exists with this email."
            );
        }

        User user = new User();

        user.setName(request.getName().trim());
        user.setEmailId(normalizedEmail);
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(Role.PATIENT);
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        Patient patient = new Patient();

        patient.setUser(savedUser);
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setAddress(request.getAddress());

        Patient savedPatient =
                patientRepository.save(patient);

        String token =
                jwtService.generateToken(savedUser);

        return new AuthResponse(
                token,
                savedUser.getUserId(),
                savedPatient.getPatientId(),
                savedUser.getName(),
                savedUser.getEmailId(),
                savedUser.getRole()
        );
    }

    public AuthResponse login(LoginRequest request) {

        String normalizedEmail =
                request.getEmail().trim().toLowerCase();

        User user = userRepository
                .findByEmailId(normalizedEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid email or password."
                        )
                );

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "This account is not active."
            );
        }

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                );

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid email or password."
            );
        }

        Long patientId = patientRepository
                .findByUserUserId(user.getUserId())
                .map(Patient::getPatientId)
                .orElse(null);

        String token =
                jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getUserId(),
                patientId,
                user.getName(),
                user.getEmailId(),
                user.getRole()
        );
    }
}