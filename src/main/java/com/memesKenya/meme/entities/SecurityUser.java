package com.memesKenya.meme.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(
        name = "users"
)
@Data
public class SecurityUser {
    @Id
    @Column(
            nullable = false
    )
    private String username;
    @Column(
            nullable = false
    )
    private String password;
    private int enabled;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username",unique = true,referencedColumnName = "username")
    private Authorities authorities;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username",unique = true,referencedColumnName = "username")
    private Memer memer;
}
