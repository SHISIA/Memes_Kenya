package com.memesKenya.meme.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Component
@AllArgsConstructor
@Data
public class Person{

    private String userName;
    private String userPassword;
    private String emailAddress;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String nickName;
    private String accountStatus;
}
