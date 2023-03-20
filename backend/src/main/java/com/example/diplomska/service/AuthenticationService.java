package com.example.diplomska.service;

import com.example.diplomska.model.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
