package com.social.network.musicians.rest;

import com.social.network.musicians.dto.ProfileMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ProfileMessageResource {

    @RequestMapping(value = "/v1/messages/artist/{artistId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProfileMessageDto> findProfileMessages(@PathVariable final String artistId){
        return List.of(ProfileMessageDto.builder().build());
    }

    @RequestMapping(value = "/v1/messages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileMessageDto insertProfileMessage(@RequestBody final ProfileMessageDto profileMessage){
        return profileMessage;
    }

    @RequestMapping(value = "/v1/messages/{messageId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProfileMessageDto updateProfileMessage(@PathVariable final String messageId, @RequestBody final ProfileMessageDto profileMessage){
        return profileMessage;
    }


}
