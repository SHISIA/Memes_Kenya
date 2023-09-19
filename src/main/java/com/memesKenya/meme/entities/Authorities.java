package com.memesKenya.meme.entities;

import com.memesKenya.meme.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorities {
    @Id
    private String username;
    private String authority;
    @OneToOne(mappedBy = "authorities")
    private SecurityUser user;
}
