package ru.rzncenter.webcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.security.TokenPreAuthenticationFilter;

/**
 * Аутентификация по токену
 */
@Component("userDetailsTokenService")
public class UserDetailsTokenServiceImpl extends AbstractUserDetails implements AuthenticationUserDetailsService<Authentication> {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
        String principal = (String) authentication.getPrincipal();
        String credentials = (String) authentication.getCredentials();
        UserDetails userDetails = null;
        if(principal != null && credentials != null && principal.equals(TokenPreAuthenticationFilter.TOKEN_PRINCIPAL)) {
            User u = userService.findByToken(credentials);
            if(u != null && u.getActive()) {
                userDetails = buildUserForAuthentication(u);
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException("Could not load user");
        }
        return userDetails;
    }

    public UserDetails buildUserForAuthentication(User u) {
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getToken(), buildUserAuthorites(u));
    }

}
