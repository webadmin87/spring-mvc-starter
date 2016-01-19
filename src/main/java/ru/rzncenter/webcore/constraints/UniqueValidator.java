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
public class UniqueValidator  implements ConstraintValidator<Unique, Object> {


    @PersistenceContext
    public EntityManager em;

    String attr;

    @Override
    public void initialize(final Unique unique) {

        attr = unique.value();

    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {

        try {

            Class cls = object.getClass();

            Object propValue = BeanUtils.getProperty(object, attr);

            String id = BeanUtils.getProperty(object, "id");

            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery criteria = builder.createQuery(cls);

            Root root = criteria.from(cls);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(attr), propValue));

            if(id != null) {

                predicates.add(builder.notEqual(root.get("id"), id));

            }

            criteria.where(predicates.toArray(new Predicate[predicates.size()]));

            Query query = em.createQuery(criteria);

            List<Object> res = query.getResultList();

            boolean result = res.size()==0;

            return result;

        } catch (Exception e) {



        }

        return true;

    }



}
