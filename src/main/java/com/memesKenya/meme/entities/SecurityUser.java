package com.memesKenya.meme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(
        name = "users"
)
@AllArgsConstructor
@NoArgsConstructor
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
