package com.social.network.musicians.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ArtistDto {

    private Long id;

    private String name;

    private String description;

}
