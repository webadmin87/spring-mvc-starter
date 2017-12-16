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

    private final String applicationName;
    private final String userfilesEnvVariable;
    private final String userfilesDirName;
    private final String contextPath;
    private final HttpServletRequest request;

    @Autowired
    public EnvironmetInfoImpl(@Value("${app.name}") String applicationName,
                              @Value("${app.userfilesEnvVariable}") String userfilesEnvVariable,
                              @Value("${app.userfilesDirName}") String userfilesDirName,
                              @Value("${app.contextPath}") String contextPath,
                              HttpServletRequest request) {
        this.applicationName = applicationName;
        this.userfilesEnvVariable = userfilesEnvVariable;
        this.userfilesDirName = userfilesDirName;
        this.contextPath = contextPath;
        this.request = request;
    }


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
