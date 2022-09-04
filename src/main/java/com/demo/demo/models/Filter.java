package com.demo.demo.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Filter {
    private String query;
    private String startedBy;
    private Timestamp dateFrom;
    private String sortBy;
}
