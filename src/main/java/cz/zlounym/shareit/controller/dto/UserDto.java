package cz.zlounym.shareit.controller.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    String email;

    String firstName;

    String lastName;

    Set<SkillDto> skillsPoses;

    Set<String> skillsToLearn;
}
