package cz.zlounym.shareit.configuration.auth;

import static org.springframework.util.StringUtils.hasText;

import java.security.Principal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImpersonatedUser implements Principal {

    private final String userName;

    private final String impersonatedBy;

    @Override
    public String getName() {
        return userName;
    }

    public String getAuditor() {
        return hasText(impersonatedBy) ? impersonatedBy : userName;
    }
}
