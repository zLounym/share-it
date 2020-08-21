package cz.zlounym.shareit.exception;

import static java.text.MessageFormat.format;

public class MissingCredentialsException extends RuntimeException {

    public MissingCredentialsException(final String parameter) {
        super(format("Parameter `{0}` is missing.", parameter));
    }
}
