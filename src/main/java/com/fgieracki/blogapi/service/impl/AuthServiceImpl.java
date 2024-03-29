package com.fgieracki.blogapi.service.impl;

import com.fgieracki.blogapi.exception.BlogAPIException;
import com.fgieracki.blogapi.model.Role;
import com.fgieracki.blogapi.model.User;
import com.fgieracki.blogapi.payload.LoginDTO;
import com.fgieracki.blogapi.payload.RegisterDTO;
import com.fgieracki.blogapi.repository.RoleRepository;
import com.fgieracki.blogapi.repository.UserRepository;
import com.fgieracki.blogapi.security.JwtTokenProvider;
import com.fgieracki.blogapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        validateUser(registerDTO);
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }

    private void validateUser(RegisterDTO registerDTO){
        if(checkIfUsernameAlreadyExists(registerDTO.getUsername()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already in use");
        if(checkIfEmailAlreadyExists(registerDTO.getEmail()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already in use");
    }

    private Boolean checkIfUsernameAlreadyExists(String username){
        return userRepository.existsByUsername(username);
    }

    private Boolean checkIfEmailAlreadyExists(String email){
        return userRepository.existsByEmail(email);
    }
}
