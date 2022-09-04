package com.demo.demo.services;

import com.demo.demo.enums.StreamStatus;
import com.demo.demo.repo.OnDemandProcessInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StreamService {
    private final OnDemandProcessInstanceRepository repository;

    public HashMap<String, StreamStatus> findStreamsStatuses() {
        var result = new HashMap<String, StreamStatus>();
        StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .forEach(instance -> result.put(instance.getId().toString(), StreamStatus.DEPLOYED));

        return result;
    }
}
