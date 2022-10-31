package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Entity
@Table(
        name = "ImagePost"
)
public class MediaTypeImage extends Post{
    @Id
    @Column(
            name = "post_Id",
            nullable = false,
            unique = true
    )
    private UUID postId;
    private IMAGE_TYPES imageType;
    private double maxSize;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "owner"
    )
    private Memer memer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "image")
    private List<Comment> comments;

}
