package cz.zlounym.shareit.controller.converter;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cz.zlounym.shareit.controller.dto.SkillDto;
import cz.zlounym.shareit.controller.request.SignUpRequest;
import cz.zlounym.shareit.controller.to.SignUpTo;
import cz.zlounym.shareit.data.domain.Skill;

@Component
public class SignUpToConverter {

    public SignUpTo convert(final SignUpRequest signUpRequest) {
        return SignUpTo.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .skillsPoses(convertSkillsPoses(signUpRequest.getSkillsPoses()))
                .skillsToLearn(convertSkillsPoses(signUpRequest.getSkillsToLearn()))
                .build();
    }

    private Set<Skill> convertSkillsPoses(final Set<SkillDto> skillsPoses) {
        return skillsPoses.stream()
                .map(skillPoses -> Skill.builder()
                        .name(skillPoses.getName())
                        .level(skillPoses.getLevel())
                        .build())
                .collect(toSet());
    }

    private Set<String> convertSkillsPoses(final String skillsPoses) {
        return stream(skillsPoses.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}