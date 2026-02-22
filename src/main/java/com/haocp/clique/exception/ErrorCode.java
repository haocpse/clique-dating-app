package com.haocp.clique.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthenticated"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "Unauthorized"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    String message;
    HttpStatusCode statusCode;

    ErrorCode(HttpStatusCode statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
