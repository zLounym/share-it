package cz.zlounym.shareit.configuration.auth;

import static java.util.Objects.isNull;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ImpersonatedUserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(final Authentication authentication) {
        if (isNull(authentication.getName())) {
            throw new UsernameNotFoundException("Username is missing");
        }
        return authentication;
    }

    @Override
    public boolean supports(final Class<?> aClass) {
        return ImpersonatedUserAuthentication.class.equals(aClass);
    }
}
