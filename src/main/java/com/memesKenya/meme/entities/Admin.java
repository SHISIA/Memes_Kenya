package com.memesKenya.meme.entities;

import com.memesKenya.meme.model.User;
import lombok.*;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "admin")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "admin")
    private List<AdminMessage> messages;

}
