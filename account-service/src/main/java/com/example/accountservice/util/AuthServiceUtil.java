package com.example.accountservice.util;

import com.example.accountservice.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(value = "auth-service")
public interface AuthServiceUtil {

    @GetMapping("/user/info")
    UserDTO getUserInfo(@RequestHeader("Authorization") String token);

}
