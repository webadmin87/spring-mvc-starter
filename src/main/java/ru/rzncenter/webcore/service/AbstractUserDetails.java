package ru.rzncenter.webcore.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.rzncenter.webcore.domains.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Родительский класс для UserDetailsService
 */
public class AbstractUserDetails {

    public List<GrantedAuthority> buildUserAuthorites(User u) {
        if(CollectionUtils.isEmpty(u.rolesList())) {
            return Collections.emptyList();
        }
        return u.rolesList().stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

}
