package com.demo.demo.spec;

import com.demo.demo.models.OnDemandProcessInstance;
import com.demo.demo.models.SearchCriteria;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Builder
public class InstanceSpecification implements Specification<OnDemandProcessInstance> {
    private final SearchCriteria criteria;
    @Override
    public Predicate toPredicate(Root<OnDemandProcessInstance> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return switch (criteria.getOperation().getValue()) {
            case "E" -> builder.equal(
                    root.get(criteria.getKey()), criteria.getValue());
            case "L" -> builder.like(
                    root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            case "I" -> builder.isNull(
                    root.get(criteria.getKey()));
            case "N" -> builder.isNotNull(
                    root.get(criteria.getKey()));
            case "G" -> builder.greaterThan(
                    root.get(criteria.getKey()), criteria.getDate());
            default -> null;
        };
    }
}
