package ru.rzncenter.webcore.validators;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;


/**
 * Валидирует сущность
 * @param <T>
 */
@Component
public class EntityValidator<T> {


    /**
     * Выполняет валидацию сущности
     * @param entity
     * @return
     */
    public Set<ConstraintViolation<T>> validate(T entity) {


        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);

        return constraintViolations;

    }

}
