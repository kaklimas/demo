package com.demo.demo.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Page {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalElements;
}
