package cz.zlounym.shareit.service;

import org.springframework.stereotype.Service;

import cz.zlounym.shareit.data.domain.User;
import cz.zlounym.shareit.data.repository.UserRepository;
import cz.zlounym.shareit.exception.UsernameTakenException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    public final UserRepository userRepository;

    public void signUp(final String username, final String password, final String firstName, final String lastName) {
        checkUserExistence(username);
        userRepository.save(User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build());
    }

    public void login(final String username, final String password) {
//        userRepository.findById(username).isPresent()
    }

    private void checkUserExistence(final String username) {
        if (userRepository.findById(username).isPresent()) {
            throw new UsernameTakenException(username);
        }
    }
}
