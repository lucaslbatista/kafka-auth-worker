package br.mil.fab.ccarj.auth.controller;

import br.mil.fab.ccarj.auth.domain.model.EnrollmentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/send")
@Slf4j
public class ProducerController {

    private final KafkaTemplate<String, EnrollmentMessage> kafkaTemplate;

    @Value("${order.topic}")
    private String topic;

//    {
//        "operation": "INSERT",
//            "cpf": "123456786",
//            "perfis": [
//        {"name": "administrador", "clientId": "eacademico"},
//        {"name": "gestor_avaliacao", "clientId": "eacademico"},
//        {"name": "administrador", "clientId": "sisplaer"}
//	]
//    }

    public ProducerController(KafkaTemplate<String, EnrollmentMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @RequestMapping(method = RequestMethod.POST)
    public void send(@RequestBody EnrollmentMessage message) {
        kafkaTemplate.send(topic, message);
    }
}
