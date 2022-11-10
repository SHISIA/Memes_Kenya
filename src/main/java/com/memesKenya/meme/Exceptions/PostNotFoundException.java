package com.memesKenya.meme.Exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
public class PostNotFoundException extends Exception {
    private String message;
    private Timestamp requestTime;
}
