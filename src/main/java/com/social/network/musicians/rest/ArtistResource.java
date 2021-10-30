package com.social.network.musicians.rest;

import com.social.network.musicians.dto.ArtistDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ArtistResource {

    @RequestMapping(value = "/v1/artists/{artistId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ArtistDto findArtistProfile(@PathVariable final String artistId) {
        return ArtistDto.builder().build();
    }

    @RequestMapping(value = "/v1/artists/{artistId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistDto createArtistProfile(@PathVariable final String artistId, @RequestBody final ArtistDto artistDto) {
        return artistDto;
    }

    @RequestMapping(value = "/v1/artists/{artistId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ArtistDto updateArtistProfile(@PathVariable final String artistId, @RequestBody final ArtistDto artistDto) {
        return artistDto;
    }

    @RequestMapping(value = "/v1/artists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistDto> findArtistByNameOrSkill(@RequestParam(required = false) final String name,
            @RequestParam(required = false) final String skill) {
        return List.of(ArtistDto.builder().build());
    }
}
