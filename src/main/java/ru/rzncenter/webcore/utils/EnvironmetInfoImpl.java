package ru.rzncenter.webcore.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Возвращает информацию об окружении 
 */
@Component
public class EnvironmetInfoImpl implements EnvironmentInfo {

    @Value("${app.name}")
    String applicationName;

    @Value("${app.userfilesEnvVariable}")
    String userfilesEnvVariable;

    @Value("${app.userfilesDirName}")
    String userfilesDirName;

    @Value("${app.contextPath}")
    String contextPath;

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
    public String getContextPath() {
        return contextPath==null?"":contextPath;
    }
}
