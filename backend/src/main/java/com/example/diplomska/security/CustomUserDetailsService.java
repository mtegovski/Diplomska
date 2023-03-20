package com.example.diplomska.security;

import com.example.diplomska.model.User;
import com.example.diplomska.service.UserService;
import com.example.diplomska.utils.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service

public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));
        return UserPrincipal.builder()
                .user(user)
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
