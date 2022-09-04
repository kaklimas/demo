package com.demo.demo.controllers;

import com.demo.demo.enums.OnDemandInstanceType;
import com.demo.demo.models.Filter;
import com.demo.demo.repo.OnDemandProcessInstanceRepository;
import com.demo.demo.models.OnDemandProcessInstance;
import com.demo.demo.services.OnDemandProcessInstanceService;
import com.demo.demo.models.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/on-demand-instances")
public class OnDemandProcessInstanceController {
    private final ObjectMapper objectMapper;
    private final OnDemandProcessInstanceService instanceService;
    private final OnDemandProcessInstanceRepository onDemandProcessInstanceRepository;

    @GetMapping
    public List<OnDemandProcessInstance> getAll() {
        return this.instanceService.getAll();
    }
    @GetMapping("/active")
    public List<OnDemandProcessInstance> getActiveInstance(@RequestParam String page, @RequestParam String filter){
        return instanceService.getOnDemandInstances(page, filter, OnDemandInstanceType.ACTIVE);
    }

    private void build() {
        var r = new Random();
        var start_dates = List.of(
                "2016-06-22 19:10:25",
                "2018-11-05 11:11:15",
                "2019-10-12 13:15:21",
                "2021-12-12 04:25:12",
                "2021-01-12 09:15:02"
        );
        var end_dates = List.of(
                "2021-12-22 19:10:25",
                "2022-01-01 13:15:21",
                "2022-12-12 04:25:12"
        );
        var started_by = List.of(
                "admin", "superadmin", "user1", "user2"
        );

        ArrayList<OnDemandProcessInstance> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            var endDate = i % 5 == 0 ? null : Timestamp.valueOf(end_dates.get(r.nextInt(2)));
            arrayList.add(
                    OnDemandProcessInstance.builder()
                            .endDate(endDate)
                            .errors(r.nextInt(1000))
                            .name("example instance %s".formatted(i))
                            .processedRecords(r.nextInt(500))
                            .startDate(Timestamp.valueOf(start_dates.get(r.nextInt(4))))
                            .startedBy(started_by.get(r.nextInt(4)))
                            .build()
            );
        }
        onDemandProcessInstanceRepository.saveAll(arrayList);
    }
}
