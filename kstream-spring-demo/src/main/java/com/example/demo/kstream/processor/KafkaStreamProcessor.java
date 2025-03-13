package com.example.demo.kstream.processor;

import java.time.Duration;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import com.example.demo.kstream.model.Agent;
import com.example.demo.kstream.model.Policy;
import com.example.demo.kstream.model.PolicyMaster;

@Component
public class KafkaStreamProcessor {

    private final JsonSerde<Agent> agentSerde;
    private final JsonSerde<Policy> policySerde;
    private final JsonSerde<PolicyMaster> masterRecordSerde;

    @Value("${topics.agent}")
    private String agentTopic;

    @Value("${topics.policy}")
    private String policyTopic;

    @Value("${topics.master}")
    private String masterTopic;

    @Value("${kafka.join-window-duration-ms}")
    private long joinWindowDurationMs;

    public KafkaStreamProcessor(JsonSerde<Agent> agentSerde, JsonSerde<Policy> policySerde,
                                JsonSerde<PolicyMaster> masterRecordSerde) {
        this.agentSerde = agentSerde;
        this.policySerde = policySerde;
        this.masterRecordSerde = masterRecordSerde;
    }

    @Bean
    public KStream<String, PolicyMaster> process(StreamsBuilder builder) {
        KStream<String, Agent> agentStream = builder.stream(agentTopic);
        KStream<String, Policy> policyStream = builder.stream(policyTopic);

        // Derive key from value if not set
        agentStream = agentStream.selectKey((key, agent) -> agent.getAgentId());
        policyStream = policyStream.selectKey((key, policy) -> policy.getAgentId());

        KStream<String, PolicyMaster> mergedStream = agentStream.join(
                policyStream,
                (agent, policy) -> {
                    PolicyMaster record = new PolicyMaster();
                    record.setAgentId(agent.getAgentId());
                    record.setAgentName(agent.getAgentName());
                    record.setRegion(agent.getRegion());
                    record.setPolicyId(policy.getPolicyId());
                    record.setPolicyType(policy.getPolicyType());
                    record.setPremiumAmount(policy.getPremiumAmount());
                    return record;
                },
                JoinWindows.of(Duration.ofMillis(joinWindowDurationMs))
        );

        // Send merged record to output topic
        mergedStream.to(masterTopic);

        return mergedStream;
    }
}
