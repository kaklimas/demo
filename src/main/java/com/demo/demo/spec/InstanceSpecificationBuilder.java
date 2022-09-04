package com.demo.demo.spec;

import com.demo.demo.models.OnDemandProcessInstance;
import com.demo.demo.models.SearchCriteria;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class InstanceSpecificationBuilder {
    private List<SearchCriteria> params;

    public void add(SearchCriteria criteria) {
        params.add(criteria);
    }

    public Specification<OnDemandProcessInstance> create() {
        if (params.isEmpty()) {
            return null;
        }
        var specs = params.stream()
                .map(param -> InstanceSpecification.builder().criteria(param).build())
                .toList();

        var result = Specification.where(specs.get(0));
        for (int i = 1; i < specs.size(); i++) {
            result =  Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
