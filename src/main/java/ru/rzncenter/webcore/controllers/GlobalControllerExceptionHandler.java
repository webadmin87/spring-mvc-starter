package ru.rzncenter.webcore.controllers;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер обработки ошибок
 */
@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * Обработка ошибок ограничений целостности
     * @param e
     */
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleConflict(DataIntegrityViolationException e) {
        logger.warn("Constraint violation: " + e.getLocalizedMessage());
    }

    /**
     * Обработка ошибок валидации
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ObjectError>> handleValidationException(MethodArgumentNotValidException e) {
        logger.warn("Validation error: " + e.getLocalizedMessage());
        return new ResponseEntity<>(e.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST); // 400
    }

}