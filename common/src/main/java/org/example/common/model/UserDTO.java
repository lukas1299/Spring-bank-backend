package org.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    private UUID userId;
    private String username;
    private String surname;
    private List<Role> roles;

}
