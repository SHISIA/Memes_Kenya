package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Entity
@Table(
        name = "VideoPost"
)
public class MediaTypeVideo extends Post{
    @Id
    @Column(
            name = "post_Id",
            nullable = false,
            unique = true
    )
    private UUID postId;

    private VIDEO_TYPES videoType;
    private double maxSize;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "owner"
    )
    private Memer memer;
}
