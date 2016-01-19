package ru.rzncenter.webcore.utils;

/**
 * Интерфейс для получения информации об окружении
 */
public interface EnvironmentInfo {
    
    public String getApplicationName();
    
    public String getUserfilesEnvVariable();
    
    public String getUserfilesDirName();

    public String getContextPath();

}
