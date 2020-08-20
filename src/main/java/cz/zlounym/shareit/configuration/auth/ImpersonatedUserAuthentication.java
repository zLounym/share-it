package cz.zlounym.shareit.configuration.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ImpersonatedUserAuthentication extends AbstractAuthenticationToken {

    private ImpersonatedUser user;

    public ImpersonatedUserAuthentication(
            final ImpersonatedUser user,
            final Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
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
