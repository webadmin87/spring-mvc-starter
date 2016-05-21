package ru.rzncenter.webcore.constraints;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Проверяет уникальность поля.
 * Пример:
 * @Unique("username")
 */
@Documented
@Constraint(validatedBy = { UniqueValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Unique {

    String value();

    String message() default "{constraints.unique}";

    String pk() default "id";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        Unique[] value();
    }

}
