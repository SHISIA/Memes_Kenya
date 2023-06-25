package com.memesKenya.meme.Exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
    private String message;
    private Timestamp requestTime;
}
