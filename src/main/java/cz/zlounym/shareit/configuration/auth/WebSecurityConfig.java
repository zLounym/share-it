package cz.zlounym.shareit.configuration.auth;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SECURED_ENDPOINT_PATTERN = "/api/**";
    private static final String OPEN_API_CREATE = "/api/sign";
    private static final String OPEN_API_ENTER = "/login";

    private final TwillAuthenticationFailureHandler twillAuthenticationFailureHandler;

    @Bean
    UserHeadersAuthenticationFilter userHeadersAuthenticationFilter() throws
            Exception {
        final UserHeadersAuthenticationFilter filter = new UserHeadersAuthenticationFilter(new SkipPathRequestMatcher(
                SECURED_ENDPOINT_PATTERN,
                List.of(OPEN_API_CREATE, OPEN_API_ENTER)
        ));
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(twillAuthenticationFailureHandler);
        return filter;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new ImpersonatedUserAuthenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http
                .addFilterBefore(
                        userHeadersAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
    }
}