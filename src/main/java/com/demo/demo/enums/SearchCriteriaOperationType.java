package com.demo.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchCriteriaOperationType {
    EQUALS("E"),
    LIKE("L"),
    IS_NULL("I"),
    IS_NOT_NULL("N"),
    // date
    GREATER_THAN("G");

    private final String value;
}
