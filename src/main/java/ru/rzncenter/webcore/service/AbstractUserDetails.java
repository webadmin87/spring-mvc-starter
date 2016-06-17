package ru.rzncenter.webcore.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.rzncenter.webcore.domains.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Родительский класс для UserDetailsService
 */
public class AbstractUserDetails {

    public List<GrantedAuthority> buildUserAuthorites(User u) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (String userRole : u.rolesList()) {
            setAuths.add(new SimpleGrantedAuthority(userRole));
        }
        List<GrantedAuthority> result = new ArrayList<>(setAuths);
        return result;
    }

}
