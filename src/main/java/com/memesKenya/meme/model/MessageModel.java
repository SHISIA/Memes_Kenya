package com.memesKenya.meme.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MessageModel {

    private UUID sender;

    private UUID recipient;

    private String message_content;

}
