package ru.rzncenter.webcore.constraints;

import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Валидатор проверяющий уникальность поля
 */
public class UniqueValidator implements ConstraintValidator<Unique, Object> {


    @PersistenceContext
    public EntityManager em;

    String attr;

    String pk;

    @Override
    public void initialize(final Unique unique) {

        attr = unique.value();

        pk = unique.pk();

    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {

        try {

            Class cls = object.getClass();

            Object propValue = BeanUtils.getProperty(object, attr);

            String id = BeanUtils.getProperty(object, pk);

            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

            Root root = criteria.from(cls);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(attr), propValue));

            if(id != null) {

                predicates.add(builder.notEqual(root.get(pk), id));

            }

            criteria.select(builder.count(root)).where(predicates.toArray(new Predicate[predicates.size()]));

            Long result = em.createQuery(criteria).getSingleResult();

            return result == 0;

        } catch (NoResultException e) {

            return true;

        } catch (Exception e) {

            return false;

        }

    }

}
