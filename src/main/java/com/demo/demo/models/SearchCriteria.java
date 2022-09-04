package com.demo.demo.models;

import com.demo.demo.enums.SearchCriteriaOperationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SearchCriteria {
    private String key;
    private SearchCriteriaOperationType operation;
    private String value;
    private Date date;
}
