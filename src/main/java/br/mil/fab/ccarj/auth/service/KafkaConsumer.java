package br.mil.fab.ccarj.auth.service;

import br.mil.fab.ccarj.auth.domain.model.ApplicationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${order.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "appMessageKafkaListenerFactory")
    public void consumer(ApplicationMessage message) {
        log.info(message.toString());
        //ApplicationMessage message = consumerRecord.value();
        //System.out.println(consumerRecord.value());
    }
}
