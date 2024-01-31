package backend.zip.security;

import backend.zip.global.exception.GeneralException;
import backend.zip.global.status.ErrorStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {
    public static String getLoggedInUserId() {
        try {
            Authentication authentication = Objects.requireNonNull(SecurityContextHolder
                    .getContext()
                    .getAuthentication());
            if (authentication instanceof AnonymousAuthenticationToken) {
                authentication = null;
            }

            return authentication.getName();
        } catch (NullPointerException e) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
    }
}
