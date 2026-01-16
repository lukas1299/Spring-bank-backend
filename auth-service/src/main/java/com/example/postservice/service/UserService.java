package com.example.postservice.service;

import com.example.postservice.model.User;
import com.example.postservice.repository.UserRepository;
import com.example.postservice.security.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UUID getUserByToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        return user.getId();
    }
}
