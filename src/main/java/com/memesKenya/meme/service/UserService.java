package com.memesKenya.meme.service;

import com.memesKenya.meme.entities.AccountStatus;
import com.memesKenya.meme.entities.Authorities;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.SecurityUser;
import com.memesKenya.meme.model.Provider;
import com.memesKenya.meme.repository.SecurityUserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.memesKenya.meme.model.Provider.*;

@Service
public class UserService {
    @Autowired
    @Lazy
    private SecurityUserRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public void processOAuthPostLogin(HttpServletResponse response,
                                              HttpServletRequest request, Authentication authentication) throws IOException {

        OAuth2User oauthUser = null;
        Provider provider=GOOGLE;
        if (request.getRequestURI().endsWith("google")){
//           Google uses DefaultOidcUser
            oauthUser = (DefaultOidcUser) authentication.getPrincipal();
        }else if (request.getRequestURI().endsWith("github")){
            provider=GITHUB;
//           GitHub uses OAuth2User
            oauthUser = (OAuth2User) authentication.getPrincipal();
        }
        else if (request.getRequestURI().endsWith("linkedin")){
            provider=LINKEDIN;
//           GitHub uses OAuth2User
            oauthUser = (OAuth2User) authentication.getPrincipal();
        }else{
            System.out.println("extra");
        }
        //currently this works for google
        String email = oauthUser.getAttribute("email");
        String picture = oauthUser.getAttribute("picture");
        String first_name = oauthUser.getAttribute("given_name");
        String last_name = oauthUser.getAttribute("family_name");
        System.out.println("Oauth "+email+" picture "+picture+" last name "+
                last_name+" first_name "+first_name);
        SecurityUser existUser=repo.findByUsername(email);

        if (existUser == null) {
            //generate a number from 0 to 20 (to be appended to the password for uniqueness)
            int randInt = (int) (Math.random() * 20);
            //create a new memer
            Memer memer = new Memer(email,first_name+last_name+randInt,
                    //convert image to a byte array for a database
                    picture,
                    email,first_name,last_name,"@"+last_name,
                    null, AccountStatus.ACTIVE.name());
            //create a new security user for Spring Security login purposes (Login table)
            SecurityUser newUser = new SecurityUser();
            newUser.setUsername(email);
            newUser.setPassword(first_name+last_name+randInt);
            newUser.setProvider(provider);
            newUser.setAuthorities(new Authorities(email,"ROLE_USER",newUser));
            newUser.setMemer(memer);
            newUser.setEnabled(1);
            repo.save(newUser);
        }else {
            System.out.println("User "+existUser.getUsername()+" is a Memer");
        }
        response.sendRedirect("/api/v1/Memers/logged");

    }

}
