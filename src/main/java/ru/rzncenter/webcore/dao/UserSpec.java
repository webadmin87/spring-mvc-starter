package ru.rzncenter.webcore.dao;

import org.springframework.data.jpa.domain.Specification;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.domains.UserFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Формирование условия выборки по пользовательскому фильтру
 */
public final class UserSpec {

    public static Specification<User> filter(final UserFilter filter) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<>();

                if(filter.getId() != null)
                    predicateList.add(criteriaBuilder.equal(root.get("id"), filter.getId()));

                if(filter.getUsername() != null)
                    predicateList.add(criteriaBuilder.like(root.<String>get("username"), "%"+filter.getUsername()+"%"));

                if(filter.getEmail() != null)
                    predicateList.add(criteriaBuilder.like(root.<String>get("email"), "%"+filter.getEmail()+"%"));

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

}
