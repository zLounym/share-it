package cz.zlounym.shareit.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignUpRequest {

    @NotBlank
    String username;

    @NotBlank
    @Size(min = 5)
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

}
