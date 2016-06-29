package ru.rzncenter.webcore.utils;

/**
 * Интерфейс для получения информации об окружении
 */
public interface EnvironmentInfo {
    
    String getApplicationName();
    
    String getUserfilesEnvVariable();
    
    String getUserfilesDirName();

    String getContextPath();

}
