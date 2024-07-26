package com.triconinfotech.WealthWise.service;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * The Class AuditorAwareImpl.
 */
public class AuditorAwareImpl implements AuditorAware<Long> {

    /**
     * Gets the current auditor.
     *
     * @return the current auditor
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve userId from the details of the Authentication object
            Object userId = authentication.getDetails();
            if (userId instanceof Long) {
                Long userIdLong = (Long) userId;
                return Optional.of(userIdLong);
            }
        }
        return Optional.empty();
    }
}
