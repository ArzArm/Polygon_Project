package com.example.spring.service;

import com.example.spring.entity.EuroToUsd;
import com.example.spring.exception.FailDataException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertorSpecificationBuilder {
    private final List<SearchCriteria> params;

    public ConvertorSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public ConvertorSpecificationBuilder with(String key, String operation, Object value) {
       if(new RequestKeyValidation().validation(key)){
           params.add(new SearchCriteria(key, operation, value));
           return this;
       } else {
           throw new FailDataException("Fail input data '" + key + "'");
       }

    }


    public Specification<EuroToUsd> build() {
        if (params.isEmpty()) {
            return null;
        }
        List<Specification> specs = params.stream()
                .map(ConvertorSpecification::new)
                .collect(Collectors.toList());
        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result =  Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
