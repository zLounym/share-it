package cz.zlounym.shareit.controller.to;

import java.util.Set;

import cz.zlounym.shareit.data.domain.Skill;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignUpTo {

    String email;

    String password;

    String firstName;

    String lastName;

    Set<Skill> skillsPoses;

    Set<String> skillsToLearn;

}
