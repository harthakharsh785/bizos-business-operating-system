package com.bizos.service;

import com.bizos.dto.AuthResponse;
import com.bizos.dto.LoginRequest;
import com.bizos.dto.RegisterRequest;
import com.bizos.entity.Organization;
import com.bizos.entity.Role;
import com.bizos.entity.User;
import com.bizos.exception.BadRequestException;
import com.bizos.repository.OrganizationRepository;
import com.bizos.repository.UserRepository;
import com.bizos.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, OrganizationRepository organizationRepository,
                        PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
                        AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        if (organizationRepository.existsByName(request.getOrganizationName())) {
            throw new BadRequestException("Organization name already taken");
        }

        Organization organization = Organization.builder()
                .name(request.getOrganizationName())
                .businessType(request.getBusinessType())
                .build();
        organization = organizationRepository.save(organization);

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .organization(organization)
                .build();
        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), organization.getId(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .email(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .organizationId(organization.getId())
                .organizationName(organization.getName())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getOrganization().getId(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .email(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .organizationId(user.getOrganization().getId())
                .organizationName(user.getOrganization().getName())
                .build();
    }
}
