package cz.zlounym.shareit.configuration.auth;

import static cz.zlounym.shareit.configuration.auth.UserHeaders.AUTHORITIES_HEADER;
import static cz.zlounym.shareit.configuration.auth.UserHeaders.IMPERSONATED_BY_HEADER;
import static cz.zlounym.shareit.configuration.auth.UserHeaders.USER_NAME_HEADER;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

public class UserHeadersAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String AUTHORITIES_HEADER_SPLITERATOR = ", ";

    public UserHeadersAuthenticationFilter(final RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse
    ) throws AuthenticationException {

        final String userName = httpServletRequest.getHeader(USER_NAME_HEADER.value());
        final String authoritiesHeader = httpServletRequest.getHeader(AUTHORITIES_HEADER.value());
        final String impersonatedBy = httpServletRequest.getHeader(IMPERSONATED_BY_HEADER.value());

        return getAuthenticationManager().authenticate(
                new ImpersonatedUserAuthentication(
                        ImpersonatedUser.builder()
                                .userName(userName)
                                .impersonatedBy(impersonatedBy)
                                .build(),
                        nonNull(authoritiesHeader) ?
                                stream(authoritiesHeader.split(AUTHORITIES_HEADER_SPLITERATOR))
                                        .filter(StringUtils::hasText)
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList())
                                : emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult
    ) throws IOException, ServletException {

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }
}
