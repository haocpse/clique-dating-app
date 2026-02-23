package com.haocp.clique.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    //400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "Password and confirm password do not match"),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "File is empty"),
    //401
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthenticated"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    //403
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "Unauthorized"),
    USER_DISABLED(HttpStatus.FORBIDDEN, "Account has been disabled"),
    //404
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "Profile not found"),
    USER_PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "Photo not found"),
    //500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    //Others
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),
    USER_PROFILE_ALREADY_EXISTS(HttpStatus.CONFLICT, "User profile already exists"),
    PHONE_NUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Phone already exists"),
    ;

    String message;
    HttpStatusCode statusCode;

    ErrorCode(HttpStatusCode statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
