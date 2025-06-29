package com.kwizera.springbootlab13trackerbooster.services;

import com.kwizera.springbootlab13trackerbooster.domain.entities.Skill;

import java.util.Optional;

public interface SkillServices {
    Skill findOrCreateSkill(String skill);

    Optional<Skill> findSkill(String skill);
}
