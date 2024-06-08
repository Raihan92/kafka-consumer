package com.progcreek.kafka.consumer.config;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:elasticsearch.properties")
public class ElasticConfig {

    private static final Logger log = LogManager.getLogger(ElasticConfig.class);

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.protocol}")
    private String protocol;

    @Bean
    public RestHighLevelClient client(){

        log.debug("Host: {}, Port: {}, Protocol: {}", host, port, protocol);

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, protocol));
        restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder.setSSLHostnameVerifier((s, sslSession) -> true));

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);

        return client;
    }
}
