package cz.zlounym.shareit.configuration.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserHeaders {

    EMAIL_HEADER("X-Consumer-Email"),
    PASSWORD_HEADER("X-Consumer-Password");

    private final String value;

    public String value() {
        return this.value;
    }

}
