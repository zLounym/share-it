package cz.zlounym.shareit.data.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Skill {

    String name;

    int level;

}
