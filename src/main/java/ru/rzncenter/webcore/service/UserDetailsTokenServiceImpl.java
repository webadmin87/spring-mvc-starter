package ru.rzncenter.webcore.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.rzncenter.webcore.domains.User;

/**
 * Аутентификация по токену
 */
@Component("userDetailsTokenService")
public class UserDetailsTokenServiceImpl extends UserDetailsServiceImpl {


    @Override
    public UserDetails buildUserForAuthentication(User u) {

        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getToken(), buildUserAuthorites(u));

    }


}
