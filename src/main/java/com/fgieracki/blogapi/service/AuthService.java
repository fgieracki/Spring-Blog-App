package com.fgieracki.blogapi.service;

import com.fgieracki.blogapi.payload.LoginDTO;
import com.fgieracki.blogapi.payload.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
