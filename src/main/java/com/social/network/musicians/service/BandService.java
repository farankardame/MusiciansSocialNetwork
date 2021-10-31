package com.social.network.musicians.service;

import com.social.network.musicians.dto.ArtistDto;
import com.social.network.musicians.dto.BandDto;
import com.social.network.musicians.entity.Band;
import com.social.network.musicians.repository.BandRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BandService {

    private BandRepository bandRepository;

    public BandDto findBandProfile(final String bandId){
        validateBandId(bandId);
        return mapBandToBandDto(bandRepository.findById(Long.valueOf(bandId)).orElse(null));
    }

    public BandDto createArtistProfile(final BandDto artistDto) {
        return mapBandToBandDto(bandRepository.save(mapBandDtoToBand(artistDto, null)));
    }

    public BandDto updateArtistProfile(final String bandId, final BandDto bandDto) {
        validateBandId(bandId);
        return mapBandToBandDto(bandRepository.save(mapBandDtoToBand(bandDto, bandId)));
    }

    private void validateBandId(String bandId) {
        Validate.notBlank(bandId, "Band Id should not be empty");
        if (!NumberUtils.isCreatable(bandId))
            throw new IllegalArgumentException("Invalid bandId " + bandId);
    }

    private BandDto mapBandToBandDto(final Band Band) {
        if (Band == null)
            return null;
        return BandDto.builder()
                .id(Band.getId())
                .description(Band.getDescription())
                .name(Band.getName())
                .build();
    }

    private Band mapBandDtoToBand(final BandDto BandDto, final String BandId) {
        if (BandDto == null)
            return null;
        return Band.builder()
                .id(StringUtils.isNotBlank(BandId) ? Long.valueOf(BandId) : BandDto.getId())
                .description(BandDto.getDescription())
                .name(BandDto.getName())
                .build();
    }
}
