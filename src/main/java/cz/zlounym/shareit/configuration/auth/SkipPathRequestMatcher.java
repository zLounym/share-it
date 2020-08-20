package cz.zlounym.shareit.configuration.auth;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class SkipPathRequestMatcher implements RequestMatcher {

    private final OrRequestMatcher ignoreMatchers;
    private final RequestMatcher processingMatcher;

    public SkipPathRequestMatcher(final String processingPath, final List<String> pathsToSkip) {
        Assert.notNull(processingPath, "processingPath is mandatory");

        processingMatcher = new AntPathRequestMatcher(processingPath);
        ignoreMatchers = CollectionUtils.isEmpty(pathsToSkip) ? null : new OrRequestMatcher(asAntMatchers(pathsToSkip));
    }

    @Override
    public boolean matches(final HttpServletRequest request) {
        return !shouldIgnore(request) && processingMatcher.matches(request);
    }

    private boolean shouldIgnore(final HttpServletRequest request) {
        return ignoreMatchers != null && ignoreMatchers.matches(request);
    }

    private static List<RequestMatcher> asAntMatchers(final List<String> pathsToSkip) {
        return pathsToSkip.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
    }
}
