package com.example.spring.service;

import com.example.spring.entity.EuroToUsd;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;

public class ConvertorSpecification implements Specification<EuroToUsd> {
    private final transient SearchCriteria criteria;

    public ConvertorSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<EuroToUsd> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (criteria.getKey().equals("date")) {
                return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), Date.valueOf(criteria.getValue().toString()));
            } else
                return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (criteria.getKey().equals("date")) {
                return builder.lessThanOrEqualTo(root.get(criteria.getKey()), Date.valueOf(criteria.getValue().toString()));
            } else
                return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("=")) {
            if (criteria.getKey().equals("date")) {
                return builder.equal(root.get(criteria.getKey()), Date.valueOf(criteria.getValue().toString()));
            } else
                return builder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
        }
        return null;
    }
}
