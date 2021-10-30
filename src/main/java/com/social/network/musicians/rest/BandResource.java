package com.social.network.musicians.rest;

import com.social.network.musicians.dto.BandDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class BandResource {

    @RequestMapping(value = "/v1/bands/{bandId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BandDto findBandProfile(@PathVariable final String bandId){
        return BandDto.builder().build();
    }

    @RequestMapping(value = "/v1/bands/{bandId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BandDto createBandProfile(@PathVariable final String bandId, @RequestBody final BandDto bandDto){
        return bandDto;
    }

    @RequestMapping(value = "/v1/bands/{bandId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BandDto updateBandProfile(@PathVariable final String bandId, @RequestBody final BandDto bandDto){
        return bandDto;
    }


}
