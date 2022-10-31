package com.memesKenya.meme.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
    @Column(
            name = "user_Id",
            nullable = false
    )
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
            name = "avatar_path",
            nullable = false
    )
    private String userAvatarPath;
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

    @Embedded
    private Message message;
}
