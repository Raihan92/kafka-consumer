package com.progcreek.kafka.consumer.controller;

import com.progcreek.kafka.consumer.dto.SubmissionsDto;
import com.progcreek.kafka.consumer.global.Reply;
import com.progcreek.kafka.consumer.service.KafkaConsumerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consume")
public class ConsumerController {

    private static Logger log = LogManager.getLogger(ConsumerController.class);

    @Value(value = "${elastic-index}")
    private String es_index;

    @Autowired
    private KafkaConsumerService consumerService;

    @GetMapping(value = "health-check")
    public Reply check() {
        return new Reply(true, "Health check success");
    }

    @GetMapping(value = "/all-submission")
    public Reply<List<SubmissionsDto>> findAllSubmission() {

        List<SubmissionsDto> submissionsDtos = consumerService.findAllSubmission(es_index);

        if(submissionsDtos != null && submissionsDtos.size() >= 0)
            return new Reply(true, HttpStatus.OK.value(), "Success!", submissionsDtos);
        else
            return new Reply(false, HttpStatus.NOT_FOUND.value(), "Not Found!");
    }
}
