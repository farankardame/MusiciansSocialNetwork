package com.social.network.musicians.dto;

import com.social.network.musicians.entity.Band;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProfileMessageDto {

    private Long id;

    private String message;

    private Band band;

    private long artistId;
}
