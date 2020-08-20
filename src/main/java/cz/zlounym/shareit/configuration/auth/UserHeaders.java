package cz.zlounym.shareit.configuration.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserHeaders {

    USER_NAME_HEADER("X-Consumer-Username"),
    AUTHORITIES_HEADER("X-Consumer-Authorities"),
    IMPERSONATED_BY_HEADER("X-Consumer-Impersonated-By");

    private final String value;

    public String value() {
        return this.value;
    }

}
