package com.memesKenya.meme.controller;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.model.Person;
import com.memesKenya.meme.service._service.MemerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    @GetMapping("/test")
    public String testApiUnsecured(){
        return "Test Successful";
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
                ).body(new ByteArrayResource(memer.getUserAvatar()));
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
}
