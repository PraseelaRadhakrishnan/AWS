package com.example.SpringBootSNSEmail.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SNSController {

    private static final Logger log = LoggerFactory.getLogger(SNSController.class);
    // Topic arn. You are free to choose their topic arn.
    private static final String TOPIC_ARN = "arn:aws:sns:ap-south-1:082683392979:springBoot-sns-topic";

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    @PostMapping(value = "/addSubscription/{email}")
    public ResponseEntity<String> addSubscription(@PathVariable final String email) {
        log.info("Adding new email subscription = {} to the topic.", email);
        final SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, "email", email);
        amazonSNSClient.subscribe(subscribeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/sendNotification")
    public ResponseEntity<String> publishMessageToTopic() {
        log.info("Publishing the notification = {} to the topic.");
        final PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, bodyMessage(), "Notification - Server failure");
        amazonSNSClient.publish(publishRequest);
        return new ResponseEntity<>("Notification sent successfully!!", HttpStatus.OK);
    }

    private String bodyMessage() {
        String body = "Hello user!!\n" +
                "\n" +
                "\n" +
                "Servers will be unavailable for next two days.";
        return body;
    }
}

