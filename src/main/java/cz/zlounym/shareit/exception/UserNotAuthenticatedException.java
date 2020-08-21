package cz.zlounym.shareit.exception;

import static java.text.MessageFormat.format;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException(final String email) {
        super(format("Wrong credentials of user {0}.", email));
    }
}
