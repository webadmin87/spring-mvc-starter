package ru.rzncenter.webcore.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.rzncenter.webcore.exceptions.ApplicationException;
import ru.rzncenter.webcore.exceptions.ErrorCodes;
import ru.rzncenter.webcore.utils.Translate;
import ru.rzncenter.webcore.web.response.BaseResponse;

import java.util.List;

/**
 * Контроллер обработки ошибок
 */
@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    private final Translate translate;

    @Autowired
    public GlobalControllerExceptionHandler(Translate translate) {
        this.translate = translate;
    }


    /**
     * Обработка ошибок ограничений целостности
     * @param e
     */
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public BaseResponse handleConflict(DataIntegrityViolationException e) {
        LOGGER.warn("Constraint violation: " + e.getLocalizedMessage());
        return new BaseResponse(ErrorCodes.DATA_INVALID, translate.t(ErrorCodes.DATA_INVALID));
    }

    /**
     * Обработка ошибок валидации
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ObjectError> handleValidationException(MethodArgumentNotValidException e) {
        LOGGER.debug("Validation error: " + e.getLocalizedMessage());
        return e.getBindingResult().getAllErrors();
    }

    /**
     * Обработчик ошибок приложения
     * @param e
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public BaseResponse handleApplicationException(ApplicationException e) {
        LOGGER.warn(e.getLocalizedMessage(), e);
        return new BaseResponse(e.getErrorCode(), translate.t(e.getErrorCode()));
    }

    /**
     * Обработчик остальных ошибок
     * @param e
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception e) {
        LOGGER.error(e.getLocalizedMessage(), e);
        return new BaseResponse(ErrorCodes.INTERNAL_ERROR, translate.t(ErrorCodes.INTERNAL_ERROR));
    }

}
