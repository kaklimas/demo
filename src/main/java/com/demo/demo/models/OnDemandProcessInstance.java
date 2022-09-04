package com.demo.demo.models;

import com.demo.demo.enums.StreamStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OnDemandProcessInstance {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private  String startedBy;
    private Timestamp startDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp endDate;

    @Transient
    private StreamStatus streamStatus;

    private Integer errors;
    private Integer processedRecords;
}