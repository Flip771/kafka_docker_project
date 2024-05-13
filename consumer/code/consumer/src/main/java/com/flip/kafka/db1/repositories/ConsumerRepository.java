package com.flip.kafka.db1.repositories;

import com.flip.kafka.db1.entities.consumer.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<ConsumerEntity, Long> {


}
