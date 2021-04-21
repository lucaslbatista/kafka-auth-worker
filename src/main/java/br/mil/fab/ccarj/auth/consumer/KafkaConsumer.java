package br.mil.fab.ccarj.auth.consumer;

import br.mil.fab.ccarj.auth.domain.model.EnrollmentMessage;
import br.mil.fab.ccarj.auth.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    private final KeycloakService keycloakService;

    public KafkaConsumer(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @KafkaListener(topics = "${order.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "appMessageKafkaListenerFactory")
    public void consumer(EnrollmentMessage message) {
        log.info("message: " + message.toString());
        keycloakService.assignUserToGroup(message);
    }
}
