package cz.zlounym.shareit.controller.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import cz.zlounym.shareit.controller.dto.SkillDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignUpRequest {

    @NotBlank
    String email;

    @NotBlank
    @Size(min = 5)
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotEmpty
    Set<SkillDto> skillsPoses;

    @NotBlank
    String skillsToLearn;

}
