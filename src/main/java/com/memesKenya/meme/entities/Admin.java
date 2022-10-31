package com.memesKenya.meme.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Component
@Entity
@Table(name = "Administrators")
public class Admin extends User {
    private String role;

}
