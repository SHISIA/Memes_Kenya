package com.memesKenya.meme.entities;

import com.memesKenya.meme.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorities {
    @Id
    @GeneratedValue
    @Column(name = "user_Id")
    private UUID uuid;
    private String username;
    private String authority;
    @OneToOne(mappedBy = "authorities")
    private SecurityUser user;

    public Authorities(String email, String roleUser, SecurityUser newUser) {
        this.username=email;
        this.authority=roleUser;
        this.user=newUser;
    }
}
