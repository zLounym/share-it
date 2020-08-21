package cz.zlounym.shareit.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginRequest {

    @NotBlank
    String email;

    @NotBlank
    String password;

}
