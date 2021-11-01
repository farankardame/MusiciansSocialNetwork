package com.social.network.musicians.dto;

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

    private Long bandId;

    private Long artistId;
}
