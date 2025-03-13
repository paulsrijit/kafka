package com.example.demo.kstream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.example.demo.kstream.model.Agent;
import com.example.demo.kstream.model.Policy;
import com.example.demo.kstream.model.PolicyMaster;

@Configuration
public class KafkaConfig {

    @Bean
    public JsonSerde<Agent> agentJsonSerde() {
        return new JsonSerde<>(Agent.class);
    }

    @Bean
    public JsonSerde<Policy> policyJsonSerde() {
        return new JsonSerde<>(Policy.class);
    }

    @Bean
    public JsonSerde<PolicyMaster> masterRecordJsonSerde() {
        return new JsonSerde<>(PolicyMaster.class);
    }
}
