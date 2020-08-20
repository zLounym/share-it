package cz.zlounym.shareit.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.zlounym.shareit.controller.request.LoginRequest;
import cz.zlounym.shareit.controller.request.SignUpRequest;
import cz.zlounym.shareit.service.AuthService;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
public class Auth2Controller {

    private final AuthService authService;

    @GetMapping("/aaa")
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

    }

}
