package com.demo.demo.services;

import com.demo.demo.enums.OnDemandInstanceType;
import com.demo.demo.enums.SearchCriteriaOperationType;
import com.demo.demo.models.Filter;
import com.demo.demo.models.OnDemandProcessInstance;
import com.demo.demo.models.SearchCriteria;
import com.demo.demo.repo.OnDemandProcessInstanceRepository;
import com.demo.demo.models.Page;
import com.demo.demo.spec.InstanceSpecificationBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.demo.demo.ConstValues.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnDemandProcessInstanceService {
    private final OnDemandProcessInstanceRepository instanceRepository;
    private final ObjectMapper objectMapper;
    private final StreamService streamService;

    private List<OnDemandProcessInstance> getRefactoredData(String pageData, String filterData, OnDemandInstanceType type) {
        var page = Page.builder().build();
        var filter = Filter.builder().build();

        try {
            page = objectMapper.readValue(pageData, Page.class);
            filter = objectMapper.readValue(filterData, Filter.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        // build query for db
        var specBuilder = InstanceSpecificationBuilder.builder().params(new ArrayList<>()).build();

        if (!type.equals(OnDemandInstanceType.ALL)) {
            specBuilder.add(
                    type.equals(OnDemandInstanceType.ACTIVE) ?
                            SearchCriteria.builder()
                                    .key(END_DATE)
                                    .operation(SearchCriteriaOperationType.IS_NOT_NULL)
                                    .build() :
                            SearchCriteria.builder()
                                    .key(END_DATE)
                                    .operation(SearchCriteriaOperationType.IS_NULL)
                                    .build()
            );
        }

        if (!filter.getQuery().equals("")){
            specBuilder.add(
                    SearchCriteria.builder()
                            .key(NAME)
                            .operation(SearchCriteriaOperationType.LIKE)
                            .value(filter.getQuery())
                            .build());
        }
        if (!filter.getStartedBy().equals("")){
            specBuilder.add(
                    SearchCriteria.builder()
                            .key(STARTED_BY)
                            .operation(SearchCriteriaOperationType.EQUALS)
                            .value(filter.getStartedBy())
                            .build()
            );
        }
        if (filter.getDateFrom() != null) {
            specBuilder.add(
                    SearchCriteria.builder()
                            .key(START_DATE)
                            .operation(SearchCriteriaOperationType.GREATER_THAN)
                            .date(new Date(filter.getDateFrom().getTime()))
                            .build()
            );
        }

        var specification = specBuilder.create();

        // care about sort
        var sort = filter.getSortBy().charAt(0) == '-' ?
                Sort.by(filter.getSortBy().substring(1)).descending() :
                Sort.by(filter.getSortBy());
        return instanceRepository.findAll(specification, PageRequest.of(page.getCurrentPage(), page.getPageSize(), sort)).getContent();
    }

    public List<OnDemandProcessInstance> getAll() {
        return (List<OnDemandProcessInstance>) instanceRepository.findAll();
    }
    public List<OnDemandProcessInstance> getOnDemandInstances(String pageData, String filterData, OnDemandInstanceType type) {
        // given page and filter
        var refactoredData = getRefactoredData(pageData, filterData, type);

        // SCDF call + merge
        var streamData = streamService.findStreamsStatuses();

        return refactoredData.stream().peek(instance -> {
            System.out.println(streamData);
            var instanceStatus = streamData.get(instance.getId().toString());
            instance.setStreamStatus(instanceStatus);
        }).toList();
    }
}
