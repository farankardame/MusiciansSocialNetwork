package com.social.network.musicians.repository;

import com.social.network.musicians.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);
}
