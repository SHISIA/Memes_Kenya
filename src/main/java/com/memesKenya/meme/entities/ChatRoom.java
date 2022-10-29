package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Component
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class ChatRoom {

    @Autowired
    private User owner,guest;


}
