package com.example.postservice.service;

import org.example.common.model.Role;
import org.example.common.model.UserDTO;
import com.example.postservice.model.User;
import com.example.postservice.repository.UserRepository;
import com.example.postservice.security.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserDTO getUserByToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        List<Role> roles = user.getRoles().stream().map(r -> new Role(r.getName())).toList();
        return new UserDTO(user.getId(), user.getUsername(), user.getSurname(), roles);
    }
}
