package com.app.socialconnection.Service;

import com.app.socialconnection.config.ConnectionRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 🎓 LEARNING: Kafka Producer Service
 *
 * This service publishes events to Kafka topics. Other microservices
 * (like Notification Service) consume these events and react to them.
 *
 * KafkaTemplate.send(topic, key, value):
 * - topic: which Kafka topic to publish to
 * - key: used for partitioning (same key → same partition → ordered)
 *         We use receiverId so all events for one user go to the same partition
 * - value: the event object (serialized to JSON by our KafkaConfig)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String CONNECTION_EVENTS_TOPIC = "connection-events";
    private static final String BLOCK_EVENTS_TOPIC = "block-events";

    public void publishConnectionEvent(ConnectionRequestEvent event) {
        log.info("Publishing connection event: {} -> {} [{}]",
                event.getSenderId(), event.getReceiverId(), event.getStatus());
        kafkaTemplate.send(CONNECTION_EVENTS_TOPIC,
                String.valueOf(event.getReceiverId()), event);
    }

    public void publishBlockEvent(ConnectionRequestEvent event) {
        log.info("Publishing block event: {} blocked {} ",
                event.getSenderId(), event.getReceiverId());
        kafkaTemplate.send(BLOCK_EVENTS_TOPIC,
                String.valueOf(event.getReceiverId()), event);
    }
}
