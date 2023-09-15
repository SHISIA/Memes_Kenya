package com.memesKenya.meme.entities;

import com.memesKenya.meme.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Authorities {
    @Id
    private String username;
    private String authority;
    @OneToOne(mappedBy = "authorities")
    private SecurityUser user;
}
