package cz.zlounym.shareit.configuration.auth;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class AuthenticationFailureResponse {

    @Builder.Default
    String title = "Unauthorized";

    @Builder.Default
    Integer status = HttpStatus.UNAUTHORIZED.value();

    String traceId;

    String spanId;
}
