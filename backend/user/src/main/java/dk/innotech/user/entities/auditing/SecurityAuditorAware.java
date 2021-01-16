package dk.innotech.user.entities.auditing;

import dk.innotech.user.security.UserDetailsFromDTO;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static dk.innotech.user.security.SecurityConstants.SYSTEM_USER_ID;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Component
public class SecurityAuditorAware implements AuditorAware<Long> {


    @Override
    public Optional<Long> getCurrentAuditor() {
        return ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    if (principal instanceof UserDetailsFromDTO) {
                        var details = (UserDetailsFromDTO)principal;
                        return details.getDTO().getId();
                    } else {
                        System.out.println("Principal is >> " + principal);
                        return SYSTEM_USER_ID;
                    }
                })
                .or(() -> of(SYSTEM_USER_ID));
    }
}
