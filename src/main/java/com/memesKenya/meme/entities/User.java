package com.memesKenya.meme.entities;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Component
public class User {
    private String userName;
    private String userPassword;
    private String userAvatarPath;
    private UUID userId;
    private Timestamp lastLoginTime;
}
