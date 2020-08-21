package cz.zlounym.shareit.configuration.auth;

import static org.springframework.util.StringUtils.hasText;

import java.security.Principal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImpersonatedUser implements Principal {

    String email;

    @Override
    public String getName() {
        return email;
    }

}
