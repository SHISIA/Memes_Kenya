package com.memesKenya.meme.entities;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Component
public class Admin extends User {
    private String role;
}
