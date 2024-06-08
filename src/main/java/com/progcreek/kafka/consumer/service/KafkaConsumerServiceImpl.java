package com.progcreek.kafka.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progcreek.kafka.consumer.dto.SubmissionsDto;
import com.progcreek.kafka.consumer.entities.Submissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaConsumerServiceImpl extends BaseService implements KafkaConsumerService {

    private static final Logger log = LogManager.getLogger(KafkaConsumerServiceImpl.class);


    @Value(value = "${elastic-index}")
    private String index;

    @Override
    @KafkaListener(topics = "${consumer-submission-topic}", groupId = "${submissions-group-id}")
    public void listen(Submissions submissions) {
        try {
            log.debug("Received message: {}", submissions.toString());
            IndexRequest request = new IndexRequest(index);
            request.source(new ObjectMapper().writeValueAsString(submissions), XContentType.JSON);
            IndexResponse indexResponse = this.getRestClient().index(request, RequestOptions.DEFAULT);
            if (indexResponse == null) {
                log.debug("Kafka topic to elasticsearch persist error");
            } else {
                log.debug("~~~~~~~~~~ Kafka topic stored into elasticsearch ~~~~~~~~~~");
            }
        } catch (Exception ex) {
            log.error("Kafka listener error: {}", ex);
        }
    }

    @Override
    public List<SubmissionsDto> findAllSubmission(String esIndex) {
        List<SubmissionsDto> submissionsDtos = new ArrayList<>();
        try {
            log.debug("----------- Received find all submissions request -----------");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.getRestClient().search(searchRequest, RequestOptions.DEFAULT);

            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                Submissions submissions = new ObjectMapper().readValue(searchHit.getSourceAsString(), Submissions.class);
                SubmissionsDto submissionsDto = new SubmissionsDto();
                BeanUtils.copyProperties(submissions, submissionsDto);
                submissionsDtos.add(submissionsDto);
            }
            log.debug("----------- Response for find all submissions -----------");
        } catch (ConnectException ex) {
            log.error("error: {}", ex);
        } catch (Exception ex) {
            log.error("error: {}", ex);
        }
        return submissionsDtos;
    }
}
