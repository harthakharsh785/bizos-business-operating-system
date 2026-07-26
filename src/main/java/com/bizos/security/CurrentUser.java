package com.bizos.security;

import com.bizos.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Small helper to fetch the logged-in user's organization/tenant context
 * anywhere in the service layer without repeating SecurityContext boilerplate.
 */
@Component
public class CurrentUser {

    public User get() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long organizationId() {
        return get().getOrganization().getId();
    }
}
