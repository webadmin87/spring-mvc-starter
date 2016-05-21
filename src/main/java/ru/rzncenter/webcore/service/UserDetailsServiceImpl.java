package ru.rzncenter.webcore.service;

import ru.rzncenter.webcore.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Аутентификация по логину и паролю
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        
        User u = userService.findByUsername(s);
        
        if(u == null || u.getActive() == false)
            throw new UsernameNotFoundException("User with name "+s+" not found");
        
        return buildUserForAuthentication(u);
        
    }
    
    public UserDetails buildUserForAuthentication(User u) {
        
        
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), buildUserAuthorites(u));
    }
    
    
    public List<GrantedAuthority> buildUserAuthorites(User u) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        for (String userRole : u.rolesList()) {
            setAuths.add(new SimpleGrantedAuthority(userRole));
        }

        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

        return result;
        
    }
    
}
