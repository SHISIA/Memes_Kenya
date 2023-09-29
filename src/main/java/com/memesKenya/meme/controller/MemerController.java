package com.memesKenya.meme.controller;

import com.memesKenya.meme.Active.LoggedOauthUser;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.model.Person;
import com.memesKenya.meme.service._service.MemerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PutMapping("/changeAvatar/{id}")
    public String changeAvatar(@PathVariable("id") UUID memerId,
                               @RequestParam("file") MultipartFile file) throws IOException {
        Memer memer=service.findById(memerId);
        return service.changeMemerAvatar(memer,file);
    }

    @GetMapping("/viewAvatar/{fileId}")
    public ResponseEntity<Resource> viewAvatar(@PathVariable UUID fileId) {
        Memer memer = service.findById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "mediaPost; filename=\""
                                + memer.getUserId()+"\""
                )
                .body(new ByteArrayResource(memer.getUserAvatar()));
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

    @GetMapping("/logged")
    public String logged(){
        return "Congratulations, Email: "+processDetails().getEmail() +" Password : "+
                processDetails().getPassword()
                +" !!! You are successfully logged in";
    }

    public LoggedOauthUser processDetails(){
        String email = "",password = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Retrieve user details
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User){
                email= ((OAuth2User) principal).getAttribute("email");
                password= ((OAuth2User) principal).getAttribute("sub");
            }else{
                email= ((DefaultOidcUser) principal).getAttribute("email");
                password= ((DefaultOidcUser) principal).getAttribute("sub");
            }
        }
        return LoggedOauthUser.builder().email(email).password(password).build();
    }
}
