package com.memesKenya.meme.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

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
    @Id
    @Column(
            name = "video_Id",
            nullable = false,
            unique = true
    )
    @GenericGenerator(name = "uuid",strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID postId;

    private String videoType;

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
