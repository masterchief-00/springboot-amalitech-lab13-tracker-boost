package com.kwizera.springbootlab13trackerbooster.services.impl;

import com.kwizera.springbootlab13trackerbooster.domain.entities.Skill;
import com.kwizera.springbootlab13trackerbooster.repositories.SkillRepository;
import com.kwizera.springbootlab13trackerbooster.services.SkillServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SkillServicesImpl implements SkillServices {
    private final SkillRepository skillRepository;

    @Override
    public Skill findOrCreateSkill(String skill) {
        Optional<Skill> skillFound = findSkill(skill);
        if (skillFound.isPresent()) return null;

        Skill newSkill = Skill.builder()
                .name(skill)
                .build();

        return skillRepository.save(newSkill);
    }

    @Override
    public Optional<Skill> findSkill(String skill) {
        return skillRepository.findByNameIgnoreCase(skill);
    }
}
