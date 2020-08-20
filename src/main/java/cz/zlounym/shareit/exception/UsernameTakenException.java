package cz.zlounym.shareit.exception;

import static java.text.MessageFormat.format;

public class UsernameTakenException extends RuntimeException {

    public UsernameTakenException(final String username) {
        super(format("Username {0} is already taken, choose different one.", username));
    }
}
