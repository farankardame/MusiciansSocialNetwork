package com.social.network.musicians.rest;

import com.social.network.musicians.dto.SkillDto;
import com.social.network.musicians.service.SkillService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class SkillsResource {

    private SkillService skillService;

    @RequestMapping(value = "/v1/skills/artist/{artistId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SkillDto> findSkills(@PathVariable final String artistId){
        return skillService.findSkills(artistId);
    }

    @RequestMapping(value = "/v1/skills/artist/{artistId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addSkill(@PathVariable final String artistId, @RequestBody SkillDto skillDto){
        skillService.addSkill(artistId,skillDto);
    }
}
