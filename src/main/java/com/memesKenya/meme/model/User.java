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
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue (generator = "UUID",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "user_Id",
            nullable = false
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID userId;

    private String userName;

    @Column(
            name = "password",
            nullable = false,
            length = 30,
            unique = true
    )
    private String userPassword;

    @Column(
            name = "avatar_path"
    )
    @Lob
    private byte[] userAvatar;
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

    public User(String username, String password, byte[] userAvatar, String emailAddress,Timestamp lastLoginTime, String firstName, String lastName,String accountStatus) {
        this.userName=username;
        this.userPassword=password;
        this.userAvatar=userAvatar;
        this.emailAddress=emailAddress;
        this.lastLoginTime=lastLoginTime;
        this.firstName=firstName;
        this.secondName=lastName;
        this.accountStatus=accountStatus;
    }
}
