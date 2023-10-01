package com.memesKenya.meme.Active;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class LoggedOauthUser {
    private String userId;
    private String nickName;
    private String image;
}
