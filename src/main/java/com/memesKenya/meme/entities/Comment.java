package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Embedded;
import java.sql.Timestamp;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private String commentOwner;
    private Timestamp timeCreated;
    @Embedded
    private Message commentMessage;
}
