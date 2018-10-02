package com.bsuir.rest.utility;

import com.bsuir.rest.model.RegisterForm;
import com.bsuir.rest.security.detail.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component(value = "securityUtil")
public class SecurityUtil {
    public static boolean isAdminOrResourceOwner(Long userId) {
        if (!isAuthenticated()) {
            return false;
        }

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userDetailsImpl.getId().equals(userId) || userDetailsImpl.getRole().equals("ADMIN")) {
            return true;
        }
        return false;
    }

    public static boolean isAdmin() {
        if (!isAuthenticated()) {
            return false;
        }

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userDetailsImpl.getRole().equals("ADMIN")) {
            return true;
        }
        return false;
    }

    public static boolean isUserRole(RegisterForm registerForm) {
        return registerForm.getRole().equals("USER");
    }

    private static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsImpl;
    }
}
