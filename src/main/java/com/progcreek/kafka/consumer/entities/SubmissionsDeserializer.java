package com.progcreek.kafka.consumer.entities;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SubmissionsDeserializer implements Deserializer<Submissions> {

    private static Logger log = LogManager.getLogger(SubmissionsDeserializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Submissions deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            log.debug("Preparing deserialization...................");
            return objectMapper.readValue(new String(data, "UTF-8"), Submissions.class);
        } catch (Exception e) {
            throw new SerializationException("Error occurred while deserializing");
        }
    }
}
