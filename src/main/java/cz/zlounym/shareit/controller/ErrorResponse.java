package cz.zlounym.shareit.controller;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class ErrorResponse {
    LocalDateTime timestamp;
    int status;
    String error;
    String message;
    String path;
    String code;
}

