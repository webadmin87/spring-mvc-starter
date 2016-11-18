package ru.rzncenter.webcore.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rzncenter.webcore.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * Аутентификация по логину и паролю
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl extends AbstractUserDetails implements UserDetailsService {

    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userService.findByUsername(s);
        if(u == null || !u.getActive()) {
            throw new UsernameNotFoundException("User with name " + s + " not found");
        }
        return buildUserForAuthentication(u);
    }
    
    public UserDetails buildUserForAuthentication(User u) {
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), buildUserAuthorites(u));
    }

}
