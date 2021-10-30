package com.social.network.musicians.rest;

import com.social.network.musicians.entity.Skill;
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

    @RequestMapping(value = "/v1/skills//artist/{artistId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Skill> findSkills(@PathVariable final String artistId){
        return List.of(Skill.builder().build());
    }
}
