package com.memesKenya.meme.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

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

}
