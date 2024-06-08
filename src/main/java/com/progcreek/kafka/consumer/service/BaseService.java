package com.progcreek.kafka.consumer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class BaseService {

    private static final Logger log = LogManager.getLogger(BaseService.class);

    @Autowired
    private RestHighLevelClient restClient;

    public RestHighLevelClient getRestClient() {
        return restClient;
    }

    private ObjectMapper objectMapper;

    protected <T> T parseResponseForObject(String json, String node, Class<T> cls) {
        T obj;
        try {

            objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            if(node != null) {
                JsonNode intermediateNode = jsonNode.path(node);
                obj = objectMapper.readValue(intermediateNode.toString(), cls);
            } else {
                obj = objectMapper.readValue(jsonNode.toString(), cls);
            }
        } catch (IOException e) {
            log.error("error {}", e);
            throw new RuntimeException("Response parsing failed");
        }
        return obj;
    }

    protected <T> List<T> parseResponseForListObject(String json, Class<T> cls) {
        List<T> obj;
        try {
            objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode payloadNode = jsonNode.path("payload");
            obj = objectMapper.readValue(payloadNode.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, cls));

        } catch (IOException e) {
            log.error("error {}", e);
            throw new RuntimeException("Response parsing failed");
        }
        return obj;
    }
}
