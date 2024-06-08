package com.progcreek.kafka.consumer.service;

import com.progcreek.kafka.consumer.dto.SubmissionsDto;
import com.progcreek.kafka.consumer.entities.Submissions;

import java.util.List;

public interface KafkaConsumerService {
    void listen(Submissions submissions);
    List<SubmissionsDto> findAllSubmission(String esIndex);
}
