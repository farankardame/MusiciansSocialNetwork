package com.social.network.musicians.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.network.musicians.dto.ArtistDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class ArtistResourceTest {

    private static final String ARTIST_ID = "1";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldFindArtist() throws Exception {
        mockMvc.perform(
                get("/v1/artists/" + ARTIST_ID)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldCreateArtistProfile() throws Exception {
        mockMvc.perform(
                post("/v1/artists")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(ArtistDto.builder().build())))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldUpdateArtistProfile() throws Exception {
        mockMvc.perform(
                put("/v1/artists/" + ARTIST_ID)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(ArtistDto.builder().build())))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldFindArtistListByName() throws Exception {
        mockMvc.perform(
                get("/v1/artists?name=Test")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldFindArtistListBySkill() throws Exception {
        mockMvc.perform(
                get("/v1/artists?skill=Test")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

}
