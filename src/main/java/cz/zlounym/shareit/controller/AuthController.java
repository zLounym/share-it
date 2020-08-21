package cz.zlounym.shareit.controller;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableSet;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.zlounym.shareit.controller.converter.SignUpToConverter;
import cz.zlounym.shareit.controller.dto.SkillDto;
import cz.zlounym.shareit.controller.dto.UserDto;
import cz.zlounym.shareit.controller.request.LoginRequest;
import cz.zlounym.shareit.controller.request.SignUpRequest;
import cz.zlounym.shareit.controller.response.UsersResponse;
import cz.zlounym.shareit.service.AuthService;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    private final SignUpToConverter signUpToConverter;

    @PostMapping("/sign")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpToConverter.convert(signUpRequest));
    }

    @GetMapping("/resource")
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @GetMapping("/users")
    public UsersResponse users() {
        final List<UserDto> userDtos = authService.users().stream()
                .map(user -> UserDto.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .skillsPoses(user.getSkillsPoses().stream()
                                .map(skill -> SkillDto.builder()
                                        .name(skill.getName())
                                        .level(skill.getLevel())
                                        .build())
                                .collect(toUnmodifiableSet()))
                        .skillsToLearn(user.getSkillsToLearn())
                        .build())
                .collect(toList());
        return UsersResponse.builder()
                .userDtos(userDtos)
                .build();
    }

    @GetMapping("/principal")
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }

    @PostMapping("/login")
    public void signUp(@RequestBody LoginRequest signUpRequest) {
        authService.login(
                signUpRequest.getEmail(),
                signUpRequest.getPassword()
        );
    }
}
