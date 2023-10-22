package com.memesKenya.meme.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class DeleteModel {
    private UUID sender;
    private UUID receiver;
}
