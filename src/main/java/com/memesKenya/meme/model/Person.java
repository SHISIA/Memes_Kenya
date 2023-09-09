package com.memesKenya.meme.model;

import com.memesKenya.meme.entities.Role;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Component
@AllArgsConstructor

public class Person{

    private String userName;
    private String userPassword;
    private String emailAddress;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String nickName;
    private String accountStatus;
    private String role;

}
