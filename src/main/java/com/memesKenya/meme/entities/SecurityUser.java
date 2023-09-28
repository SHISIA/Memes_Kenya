package com.memesKenya.meme.entities;

import com.memesKenya.meme.model.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
    @GeneratedValue (generator = "UUID",strategy = GenerationType.AUTO)
    @Column(
            name = "user_Id",
            nullable = false
    )
    private UUID userId;
    @Column(
            nullable = false
    )
    private String username;
    @Column(
            nullable = false
    )
    private String password;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private int enabled;
    private String sub_Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id",unique = true,referencedColumnName = "user_Id")
    private Authorities authorities;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id",unique = true,referencedColumnName = "user_Id")
    private Memer memer;

    public SecurityUser(String userName, String password, Provider provider, int enabled, String sub_Id,Authorities authorities, Memer memer) {
        this.username=userName;
        this.password=password;
        this.enabled=enabled;
        this.provider=provider;
        this.sub_Id=sub_Id;
        this.authorities=authorities;
        this.memer=memer;
    }
}
