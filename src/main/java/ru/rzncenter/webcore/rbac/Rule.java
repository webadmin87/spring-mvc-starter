package ru.rzncenter.webcore.rbac;

import org.springframework.security.core.Authentication;

/**
 * Created by anton on 08.02.15.
 */
public interface Rule {
    
    boolean execute(Authentication auth, UserDomain user, Object domain);
    
}
