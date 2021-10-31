package com.social.network.musicians.service;

import com.social.network.musicians.dto.SkillDto;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillService {

    private SkillsRepository skillsRepository;

    private ArtistRepository artistRepository;

    public List<SkillDto> findSkills(final String artistId){
        validateId(artistId);

        Optional<Artist> artist = artistRepository.findById(Long.valueOf(artistId));

        if(artist.isPresent()){
            return artist.get().getSkills().stream()
                     .map(this::mapSkillToSkillDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    public void addSkill(final String artistId, final SkillDto skillDto){
        validateId(artistId);
        Artist artist = artistRepository.findById(Long.valueOf(artistId)).orElse(null);

        if(artist != null && !skillAlreadyPresent(skillDto.getName(), artist.getSkills())) {
            Optional<Skill> optionalSkill = skillsRepository.findByName(skillDto.getName());
            Skill skill = optionalSkill.orElse(mapSkillDtoToSkill(skillDto, null));
            artist.getSkills().add(skill);
            artistRepository.save(artist);
        }
    }

    private boolean skillAlreadyPresent(final String skillName, final Set<Skill> skills){
        return skills.stream()
                .anyMatch(skill -> skillName.equalsIgnoreCase(skill.getName()));
    }

    private void validateId(String id) {
        Validate.notBlank(id, "Id should not be empty");
        if (!NumberUtils.isCreatable(id))
            throw new IllegalArgumentException("Invalid ID " + id);
    }

    private SkillDto mapSkillToSkillDto(final Skill skill) {
        if (skill == null)
            return null;
        return SkillDto.builder()
                .id(skill.getId())
                .description(skill.getDescription())
                .name(skill.getName())
                .build();
    }

    private Skill mapSkillDtoToSkill(final SkillDto skillDto, final String skillId) {
        if (skillDto == null)
            return null;
        return Skill.builder()
                .id(StringUtils.isNotBlank(skillId) ? Long.valueOf(skillId) : skillDto.getId())
                .description(skillDto.getDescription())
                .name(skillDto.getName())
                .build();
    }

}
