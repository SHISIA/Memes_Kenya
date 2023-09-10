package com.memesKenya.meme.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
