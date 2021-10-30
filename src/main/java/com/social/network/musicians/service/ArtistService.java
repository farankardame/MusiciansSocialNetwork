package com.social.network.musicians.service;

import com.social.network.musicians.dto.ArtistDto;
import com.social.network.musicians.entity.Artist;
import com.social.network.musicians.entity.Skill;
import com.social.network.musicians.repository.ArtistRepository;
import com.social.network.musicians.repository.SkillsRepository;
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
public class ArtistService {

    private ArtistRepository artistRepository;

    private SkillsRepository skillsRepository;

    public ArtistDto findArtistProfile(final String artistId) {
        validateArtistId(artistId);
        return mapArtistToArtistDto(artistRepository.findById(Long.valueOf(artistId)).orElse(null));
    }

    public ArtistDto createArtistProfile(final ArtistDto artistDto) {
        return mapArtistToArtistDto(artistRepository.save(mapArtistDtoToArtist(artistDto, null)));
    }

    public ArtistDto updateArtistProfile(final String artistId, final ArtistDto artistDto) {
        validateArtistId(artistId);
        return mapArtistToArtistDto(artistRepository.save(mapArtistDtoToArtist(artistDto, artistId)));
    }

    public List<ArtistDto> findArtistByNameOrSkill(final String name, final String skill) {

        if (StringUtils.isNotBlank(name)) {
            return artistRepository.findByName(name).stream()
                    .map(this::mapArtistToArtistDto)
                    .collect(Collectors.toList());
        }

        if (StringUtils.isNotBlank(skill)) {
            Optional<Skill> skills = skillsRepository.findByName(skill);
            if(skills.isEmpty()) return null;
            return skills.get().getArtists().stream()
                    .map(this::mapArtistToArtistDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    private void validateArtistId(String artistId) {
        Validate.notBlank(artistId, "Artist Id should not be empty");
        if (!NumberUtils.isCreatable(artistId))
            throw new IllegalArgumentException("Invalid artistId " + artistId);
    }

    private ArtistDto mapArtistToArtistDto(final Artist artist) {
        if (artist == null)
            return null;
        return ArtistDto.builder()
                .id(artist.getId())
                .description(artist.getDescription())
                .name(artist.getName())
                .build();
    }

    private Artist mapArtistDtoToArtist(final ArtistDto artistDto, final String artistId) {
        if (artistDto == null)
            return null;
        return Artist.builder()
                .id(StringUtils.isNotBlank(artistId) ? Long.valueOf(artistId) : artistDto.getId())
                .description(artistDto.getDescription())
                .name(artistDto.getName())
                .build();
    }
}
