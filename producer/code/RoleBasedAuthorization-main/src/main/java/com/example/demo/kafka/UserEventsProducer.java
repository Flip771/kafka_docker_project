package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventsProducer {

    private static final Logger LOG = LoggerFactory.getLogger(UserEventsProducer.class);

    private KafkaTemplate<String,String> kafkaTemplate;

    public UserEventsProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

}
