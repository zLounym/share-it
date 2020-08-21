package cz.zlounym.shareit.configuration.auth;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class ImpersonatedUserAuthentication extends AbstractAuthenticationToken {

    private final ImpersonatedUser user;

    public ImpersonatedUserAuthentication(final ImpersonatedUser user) {
        super(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return user.getName();
    }
}
