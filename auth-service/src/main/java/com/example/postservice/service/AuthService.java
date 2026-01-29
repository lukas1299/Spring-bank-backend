package com.example.postservice.service;

import org.example.common.model.AccountDTO;
import com.example.postservice.DTO.LoginResponse;
import com.example.postservice.DTO.TokenRequest;
import com.example.postservice.DTO.UserRegisterRequest;
import com.example.postservice.DTO.UserResponse;
import com.example.postservice.exception.UsernameAlreadyExistException;
import com.example.postservice.model.Role;
import com.example.postservice.model.User;
import com.example.postservice.repository.RoleRepository;
import com.example.postservice.repository.UserRepository;
import com.example.postservice.security.JwtTokenUtil;
import com.example.postservice.util.AccountServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountServiceUtil accountServiceUtil;

    private final JwtTokenUtil jwtTokenUtil;

    public TokenRequest login(LoginResponse userResponse) {

        User user = userRepository.findByUsername(userResponse.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User does not exists."));

        if (!passwordEncoder.matches(userResponse.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Incorrect password.");
        }

        return new TokenRequest(jwtTokenUtil.generateToken(userResponse.getLogin(), user.getRoles()));
    }

    public UserRegisterRequest register(UserResponse userResponse) throws UsernameAlreadyExistException {

        if (userRepository.findByUsername(userResponse.getUsername()).isPresent()) throw new UsernameAlreadyExistException("Username already exist.");

        User user = createUser(userResponse);

        AccountDTO account = accountServiceUtil.createAccount(user.getId());

        List<Role> roles = roleRepository.findByNameIn(userResponse.getRoles().stream().toList());

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return new UserRegisterRequest(savedUser.getUsername(), savedUser.getSurname(), userResponse.getPassword(), roles.stream().map(Role::getName).collect(Collectors.toSet()),account);
    }

    private User createUser(UserResponse userResponse) {
        return new User(UUID.randomUUID(),
                userResponse.getUsername(),
                userResponse.getSurname(),
                passwordEncoder.encode(userResponse.getPassword()),
                List.of());
    }
}
