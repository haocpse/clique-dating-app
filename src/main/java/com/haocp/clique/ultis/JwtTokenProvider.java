package com.haocp.clique.ultis;

import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtTokenProvider {

    public static Long getCurrentUserId() {
        Jwt jwt = getJwt();
        if (jwt == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Object value = jwt.getClaim("userId");
        if (value == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (value instanceof Number n) {
            return n.longValue();
        }
        try {
            return Long.getLong(value.toString());
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private static Jwt getJwt() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt;
    }
}
