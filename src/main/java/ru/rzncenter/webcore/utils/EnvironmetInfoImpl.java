package ru.rzncenter.webcore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Возвращает информацию об окружении 
 */
@Component
public class EnvironmetInfoImpl implements EnvironmentInfo {

    @Value("${app.name}")
    private String applicationName;

    @Value("${app.userfilesEnvVariable}")
    private String userfilesEnvVariable;

    @Value("${app.userfilesDirName}")
    private String userfilesDirName;

    @Value("${app.contextPath}")
    private String contextPath;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public String getUserfilesEnvVariable() {
        return userfilesEnvVariable;
    }

    @Override
    public String getUserfilesDirName() {
        return userfilesDirName;
    }

    @Override
    public String getContextPath() { return request!=null?request.getContextPath():contextPath; }
}
