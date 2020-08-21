package cz.zlounym.shareit.exception;

import static java.text.MessageFormat.format;

public class EmailTakenException extends RuntimeException {

    public EmailTakenException(final String email) {
        super(format("Email {0} is already taken, choose different one.", email));
    }
}
