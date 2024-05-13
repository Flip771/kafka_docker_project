package com.flip.kafka.kafka;



import com.flip.kafka.db1.entities.consumer.ConsumerEntity;
import com.flip.kafka.db1.repositories.ConsumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);
    @Autowired
    private ConsumerRepository consumerRepository;



    @KafkaListener(topics="user-events",groupId = "myGroup")
    public void consume(String eventMessage){
        LOG.info("Consuming " + eventMessage);

        ConsumerEntity consumerEntity = new ConsumerEntity();
        consumerEntity.setCreatedOn(new Date());
        consumerEntity.setUserEventData(eventMessage);
        consumerRepository.save(consumerEntity);

    }
}
