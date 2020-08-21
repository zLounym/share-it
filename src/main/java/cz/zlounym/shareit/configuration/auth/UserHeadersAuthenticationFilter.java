package cz.zlounym.shareit.configuration.auth;

import static cz.zlounym.shareit.configuration.auth.UserHeaders.PASSWORD_HEADER;
import static cz.zlounym.shareit.configuration.auth.UserHeaders.EMAIL_HEADER;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import cz.zlounym.shareit.service.AuthService;

public class UserHeadersAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthService authService;

    public UserHeadersAuthenticationFilter(
            final RequestMatcher requiresAuthenticationRequestMatcher,
            final AuthService authService
    ) {
        super(requiresAuthenticationRequestMatcher);
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse
    ) {
        final String email = httpServletRequest.getHeader(EMAIL_HEADER.value());
        final String password = httpServletRequest.getHeader(PASSWORD_HEADER.value());

        authService.checkUserCredentials(email, password);

        return getAuthenticationManager().authenticate(
                new ImpersonatedUserAuthentication(
                        ImpersonatedUser.builder()
                                .email(email)
                                .build()
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
