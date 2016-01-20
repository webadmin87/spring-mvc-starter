package ru.rzncenter.webcore.validators.ngrequired;

/**
 * Результат валидации angular
 */
public class Result {

    boolean isValid;

    String value;

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
