package ru.rzncenter.webcore.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    String loginUrl = "/login";

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException

	{

        String xReq = request.getHeader("X-Requested-With");

        if(xReq !=null && xReq.equals("XMLHttpRequest")) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        } else {

            response.sendRedirect( request.getContextPath() + getLoginUrl());

        }



	}

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
