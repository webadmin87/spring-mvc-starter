package ru.rzncenter.webcore.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.domains.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обработчик успешной аутентификации
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserDao userDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        User user = userDao.findByUsername(username);

        if(user != null) {

            String token = user.getToken();

            this.clearAuthenticationAttributes(httpServletRequest);

            httpServletResponse.setHeader("X-AUTH-TOKEN", token);

            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(user);

            httpServletResponse.getWriter().write(json);

            httpServletResponse.getWriter().close();

        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }


    }
}
