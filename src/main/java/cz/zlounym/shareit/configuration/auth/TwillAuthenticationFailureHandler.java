package cz.zlounym.shareit.configuration.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TwillAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException exception
    ) throws IOException {
        log.warn("{}: {}", HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage());

        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        objectMapper.writeValue(response.getOutputStream(), AuthenticationFailureResponse.builder()
//                .traceId(getContext().map(SpanContext::toTraceId).orElse(""))
//                .spanId(getContext().map(SpanContext::toSpanId).orElse(""))
                .build());
    }

//    private Optional<SpanContext> getContext() {
//        return Optional.ofNullable(GlobalTracer.get())
//                .map(Tracer::activeSpan)
//                .map(Span::context);
//    }
}
