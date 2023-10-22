package com.memesKenya.meme.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderAndReceiverModel {
    private UUID sender;
    private UUID receiver;
}
