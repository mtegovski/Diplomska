package com.example.diplomska.controller;

import com.example.diplomska.model.Role;
import com.example.diplomska.security.UserPrincipal;
import com.example.diplomska.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("change/{role}")
    private ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Role role) {
        this.userService.updateUserRole(userPrincipal.getEmail(), role);

        return ResponseEntity.ok(true);
    }
}
