package ru.rzncenter.webcore.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Фильтр пре аутентифкации по токену
 */
public class TokenPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    public static final String TOKEN_PRINCIPAL = "TOKEN_PRINCIPAL";
    public static final String  TOKEN_HEADER = "X-AUTH-TOKEN";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(TOKEN_HEADER);
        return token!=null?TOKEN_PRINCIPAL:null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(TOKEN_HEADER);
    }
}
