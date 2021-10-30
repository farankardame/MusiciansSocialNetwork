package com.social.network.musicians.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.network.musicians.dto.BandDto;
import com.social.network.musicians.entity.ProfileMessage;
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
public class ProfileMessageResourceTest {

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
    public void shouldFindProfileMessages() throws Exception {
        mockMvc.perform(
                get("/v1/messages/artist/" + ARTIST_ID)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldCreateProfileMessage() throws Exception {
        mockMvc.perform(
                post("/v1/messages")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(ProfileMessage.builder().build())))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldUpdateProfileMessage() throws Exception {
        mockMvc.perform(
                put("/v1/messages/" + ARTIST_ID)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(ProfileMessage.builder().build())))
                .andExpect(status().isOk());

    }
}
