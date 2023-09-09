package com.memesKenya.meme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@AllArgsConstructor
@Component
@Entity
@AutoConfiguration
@Table(
        name = "Roles"
)
public class Role {
    @Id
    @GeneratedValue (generator = "UUID",strategy = GenerationType.AUTO)
    @Column(
            name = "Role_Id",
            nullable = false
    )
    private UUID userId;

    private String name;
}