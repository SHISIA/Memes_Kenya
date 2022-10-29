package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class MediaTypeVideo extends Post {
    private String videoType;
}
