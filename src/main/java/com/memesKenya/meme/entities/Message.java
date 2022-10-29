package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Message extends Memer{
    private String message_Info;
    private Timestamp timeCreated;
}
