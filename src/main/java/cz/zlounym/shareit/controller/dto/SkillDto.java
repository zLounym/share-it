package cz.zlounym.shareit.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SkillDto {

    String name;

    int level;

}
