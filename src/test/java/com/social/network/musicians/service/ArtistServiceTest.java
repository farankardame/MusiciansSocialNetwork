package com.social.network.musicians.service;

import com.social.network.musicians.dto.ArtistDto;
import com.social.network.musicians.entity.Artist;
import com.social.network.musicians.repository.ArtistRepository;
import com.social.network.musicians.repository.SkillsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private SkillsRepository skillsRepository;

    private ArtistService artistService;

    @BeforeEach
    public void setup(){
        artistService = new ArtistService(artistRepository, skillsRepository);
    }

    @Test
    public void shouldGetArtistByArtistId(){
        ArtistDto expectedArtistDto = ArtistDto.builder().id(1L).build();
        Mockito.when(artistRepository.findById(1L)).thenReturn(Optional.of(Artist.builder().id(1L).build()));
        Assertions.assertEquals(expectedArtistDto.getId(), artistService.findArtistProfile("1").getId());
    }

    @Test
    public void shouldCreateArtist(){
        ArtistDto expectedArtistDto = ArtistDto.builder().id(1L).build();
        Artist artist = Artist.builder().id(1L).build();
        Mockito.when(artistRepository.save(artist)).thenReturn(artist);
        Assertions.assertEquals(expectedArtistDto.getId(), artistService.createArtistProfile(ArtistDto.builder().id(1L).build()).getId());
    }

}
