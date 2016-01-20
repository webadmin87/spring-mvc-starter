package ru.rzncenter.webcore.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Компонент определяющий критерии, которым должен соответствовать запрос для применения к нему фильтра аутентификации по токену
 */
public class TokenRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(CustomTokenAuthenticationFilter.HEADER_SECURITY_TOKEN);
        return  token != null && token.length()>0;
    }
}
