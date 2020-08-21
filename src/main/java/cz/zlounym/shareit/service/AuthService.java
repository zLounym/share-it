package cz.zlounym.shareit.service;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.stereotype.Service;

import cz.zlounym.shareit.controller.to.SignUpTo;
import cz.zlounym.shareit.data.domain.User;
import cz.zlounym.shareit.data.repository.UserRepository;
import cz.zlounym.shareit.exception.MissingCredentialsException;
import cz.zlounym.shareit.exception.UserNotAuthenticatedException;
import cz.zlounym.shareit.exception.EmailTakenException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    public final UserRepository userRepository;

    public void signUp(final SignUpTo signUpTo) {
        checkUserExistence(signUpTo.getEmail());
        userRepository.save(User.builder()
                .email(signUpTo.getEmail())
                .password(signUpTo.getPassword())
                .firstName(signUpTo.getFirstName())
                .lastName(signUpTo.getLastName())
                .skillsPoses(signUpTo.getSkillsPoses())
                .skillsToLearn(signUpTo.getSkillsToLearn())
                .build());
    }

    public void login(final String email, final String password) {
        checkUserCredentials(email, password);
    }

    public void checkUserCredentials(final String email, final String password) {
        checkInputs(email, password);
        if (userRepository.findByEmailAndPassword(email, password).isEmpty()) {
            throw new UserNotAuthenticatedException(email);
        }
    }

    public List<User> users() {
        return userRepository.findAll();
    }

    private void checkUserExistence(final String email) {
        if (userRepository.findById(email).isPresent()) {
            throw new EmailTakenException(email);
        }
    }

    private void checkInputs(final String email, final String password) {
        if (isNull(email)) {
            throw new MissingCredentialsException("email");
        }
        if (isNull(password)) {
            throw new MissingCredentialsException("password");
        }
    }
}
