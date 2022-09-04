package com.demo.demo.models;

import com.demo.demo.enums.StreamStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SCDFProcessInstance {
    private String name;
    private StreamStatus streamStatus;

}
