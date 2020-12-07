package com.example.sqs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sqs")
public class SQSController {

    private static final Logger LOG = LoggerFactory.getLogger(SQSController.class);

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    @PostMapping("/sendUsingSQSUri")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void sendMessageToUri() {
        LOG.info("Sending the message to Amazon sqs through Uri.");
        queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload("hello from Spring Boot").build());
        LOG.info("Message sent successfully to the Amazon sqs through Uri.");
    }

    @PostMapping(value = "/sendUsingSQSQueue/{message}")
    @ResponseStatus(code = HttpStatus.CREATED)
    void sendMessageToQueue(@PathVariable("message") final String message) {
        LOG.info("Sending the message to the Amazon sqs through queue name.");
        queueMessagingTemplate.convertAndSend("springBoot-sqs-queue", message);
        LOG.info("Message sent successfully to the Amazon sqs through queue name..");
    }

    @SqsListener("springBoot-sqs-queue")
    void getMessage(String message) {
        LOG.info("Message from SQS Queue - "+ message);
    }
}

