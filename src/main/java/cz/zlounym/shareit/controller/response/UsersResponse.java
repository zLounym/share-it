package cz.zlounym.shareit.controller.response;

import java.util.List;

import cz.zlounym.shareit.controller.dto.UserDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UsersResponse {

    List<UserDto> userDtos;

}
