package ru.rzncenter.webcore.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by anton on 26.06.15.
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String returnUrl = httpServletRequest.getParameter("j_return_url");

        if(returnUrl != null && returnUrl.length()>0) {

            this.getRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, returnUrl);

            this.clearAuthenticationAttributes(httpServletRequest);

        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }


    }
}
