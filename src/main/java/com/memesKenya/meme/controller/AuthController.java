package com.memesKenya.meme.controller;

import com.memesKenya.meme.model.LoginRequest;
import com.memesKenya.meme.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger LOG= LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService=tokenService;
    }

    @GetMapping("")
    public String token(@RequestBody LoginRequest userLogin){
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userLogin.userName(),userLogin.password()
        ));
        return tokenService.generateToken(authentication);
    }

}
