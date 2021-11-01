package com.social.network.musicians.service;

import com.social.network.musicians.dto.ProfileMessageDto;
import com.social.network.musicians.entity.Artist;
import com.social.network.musicians.entity.Band;
import com.social.network.musicians.entity.ProfileMessage;
import com.social.network.musicians.entity.Skill;
import com.social.network.musicians.repository.ArtistRepository;
import com.social.network.musicians.repository.BandRepository;
import com.social.network.musicians.repository.ProfileMessageRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileMessageService {

    private ArtistRepository artistRepository;

    private BandRepository bandRepository;

    private ProfileMessageRepository profileMessageRepository;

    public List<ProfileMessageDto> findProfileMessages(final String artistId) {
        validateId(artistId);

        Optional<Artist> artist = artistRepository.findById(Long.valueOf(artistId));

        if (artist.isPresent()) {
            return artist.get().getProfileMessages().stream()
                    .map(this::mapProfileMessageToProfileMessageDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    public ProfileMessageDto profileMessageService(final ProfileMessageDto profileMessage) {
        return mapProfileMessageToProfileMessageDto(
                profileMessageRepository.save(mapProfileMessageDtoToProfileMessage(profileMessage, null)));
    }

    public ProfileMessageDto updateProfileMessage(final String messageId, final ProfileMessageDto profileMessageDto) {
        validateId(messageId);
        return mapProfileMessageToProfileMessageDto(
                profileMessageRepository.save(mapProfileMessageDtoToProfileMessage(profileMessageDto, messageId)));
    }

    private void validateId(String id) {
        Validate.notBlank(id, "Id should not be empty");
        if (!NumberUtils.isCreatable(id))
            throw new IllegalArgumentException("Invalid ID " + id);
    }

    private ProfileMessageDto mapProfileMessageToProfileMessageDto(final ProfileMessage profileMessage) {
        if (profileMessage == null)
            return null;
        return ProfileMessageDto.builder()
                .id(profileMessage.getId())
                .message(profileMessage.getMessage())
                .artistId(profileMessage.getArtist()!=null?profileMessage.getArtist().getId():null)
                .bandId(profileMessage.getBand()!=null?profileMessage.getBand().getId():null)
                .build();
    }

    private ProfileMessage mapProfileMessageDtoToProfileMessage(final ProfileMessageDto profileMessageDto,
            final String profileMessageId) {
        if (profileMessageDto == null)
            return null;

        Artist artist = null;
        Band band = null;

        if(profileMessageDto.getArtistId()!=null){
            artist = artistRepository.findById(profileMessageDto.getArtistId()).orElse(null);
        }
        if(profileMessageDto.getBandId()!=null){
            band = bandRepository.findById(profileMessageDto.getBandId()).orElse(null);
        }
        return ProfileMessage.builder()
                .id(StringUtils.isNotBlank(profileMessageId) ?
                        Long.valueOf(profileMessageId) :
                        profileMessageDto.getId())
                .message(profileMessageDto.getMessage())
                .artist(artist)
                .band(band)
                .build();
    }

}
