package com.memesKenya.meme.controller;

import com.memesKenya.meme.Active.LoggedOauthUser;
import com.memesKenya.meme.Active.OauthUserMail;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.SecurityUser;
import com.memesKenya.meme.model.Person;
import com.memesKenya.meme.service._service.MemerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/Memers")
public class MemerController {
    @Autowired
    private MemerService service;

    @GetMapping("/allMemers")
    public List<Memer> getAllMemers(){
        return service.getAllMemers();
    }

    @GetMapping("/loggedOut")
    public String loggedOut(){
        return "Successfully Logged Out";
    }

    @GetMapping("/test")
    public Map<String, String> testApiUnsecured(HttpSession session){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Test Successful");
        return response;
    }
    @PostMapping("/newMemer")
    public String newMemerAccount(@RequestBody Person person) {
        boolean saved=service.registerNewMemer(person);
        return saved ? "Successfully registered "+person.getFirstName()+" "+person.getSecondName()
                : "Error creating user. Try again";
    }

    @PutMapping("/changeAvatar/{id}/{link}")
    public String changeAvatar(@PathVariable("id") UUID memerId,
                               @PathVariable("link") String hostedLink) throws IOException {
        Memer memer=service.findById(memerId);
        return service.changeMemerAvatar(memer,hostedLink);
    }

    @GetMapping("/getMemerByNickName/{nick}")
    public Memer getByNickName(@PathVariable("nick") @Validated String nickName){
        if (nickName.startsWith("@")){
            nickName=nickName.replace("@","");
        }
        return service.getMemerByNickName("@"+nickName);
    }

    @GetMapping("/getMemerByName/{text}")
    public Memer getByAnyName(@PathVariable("text") String nameOrPhone){
        return service.getMemerByAnyName(nameOrPhone);
    }

    @GetMapping("/storeData")
    public OauthUserMail storeData(Principal principal) {
        return OauthUserMail.builder().email(principal.getName()).build();
    }

    @GetMapping("/data/{id}")
    public LoggedOauthUser getActiveLoggedUser(@PathVariable("id") String userId){
        Optional<SecurityUser> securityUser= Optional.ofNullable(service.findBySubId(userId));
        if (securityUser.isEmpty()){
            return null;
        }
        Memer memer = service.findByUsername(securityUser.get().getUsername());
        System.out.println("Data sent!!!");
        return LoggedOauthUser.builder()
                .userId(String.valueOf(memer.getUserId()))
                .image(memer.getUserAvatar())
                .nickName(memer.getNickName())
                .build();
    }
}
