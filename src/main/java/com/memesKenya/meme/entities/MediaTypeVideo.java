package com.memesKenya.meme.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Entity
@Table(
        name = "VideoPost"
)
public class MediaTypeVideo extends Post{

    private VIDEO_TYPES videoType;
    private double maxSize;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "video")
    private List<Comment> comments;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "PostOwner"
    )
    private Memer memer;
}
