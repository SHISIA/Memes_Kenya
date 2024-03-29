package com.memesKenya.meme.controller;

import com.memesKenya.meme.model.LoginRequest;
import com.memesKenya.meme.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private static final Logger LOG= LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService=tokenService;
    }

    @PostMapping("/authenticate")
    public Map<String,String> token(@RequestBody LoginRequest loginRequest){
        System.out.println("Username "+loginRequest.username()+" password "+loginRequest.password());
       Authentication authentication= authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
            loginRequest.username(),
                    loginRequest.password()
        ));
        String token = tokenService.generateToken(authentication,
                1, ChronoUnit.DAYS);
        Map<String,String> stringStringMap=new HashMap<>();
        stringStringMap.put("token",token);
        return stringStringMap;
    }

}
