package com.memesKenya.meme.model;

import com.memesKenya.meme.entities.Authorities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Component
@AllArgsConstructor
@MappedSuperclass
public class User {
    @Id
//    @GeneratedValue (generator = "UUID",strategy = GenerationType.AUTO)
    @Column(
            name = "user_Id",
            nullable = false
    )
    private UUID userId;

    @Column(name = "username",nullable = false)
    private String userName;

    @Column(
            name = "password",
            nullable = false,
            unique = true
    )
    private String userPassword;

    @Column(
            name = "avatar_path"
    )
//    @Lob
    private String userAvatar;
    @Column(
            name = "email_address",
            nullable = false,
            unique = true
    )
    private String emailAddress;
    @Column(
            name = "lastLogged"
    )
    private Timestamp lastLoginTime;
    private String firstName;
    private String secondName;
    private String accountStatus;
//    public User(UUID userId,String username, String password, String userAvatar, String emailAddress,Timestamp lastLoginTime, String firstName, String lastName,
//                String accountStatus) {
//        this.userName=username;
//        this.userId=userId;
//        this.userPassword=password;
//        this.userAvatar=userAvatar;
//        this.emailAddress=emailAddress;
//        this.lastLoginTime=lastLoginTime;
//        this.firstName=firstName;
//        this.secondName=lastName;
//        this.accountStatus=accountStatus;
//    }
}
