package com.eventBridge.in.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventBridge.in.model.Input;
import com.eventBridge.in.services.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.eventbridge.model.Rule;

@RestController
@Slf4j
public class EventBridgeController {

	@Value("${cloud.aws.sqs.queue-name}")
	private String QUEUE;

	@Autowired
	private EventService service;

	@PostMapping("/send-event")
	public ResponseEntity<String> sendEvent(@RequestBody Input input) {
		service.sendEvent(input);
		return new ResponseEntity<>("Event sent successfully", HttpStatus.OK);
	}

	@GetMapping("/rules")
	public ResponseEntity<List<Rule>> getAllRules() {
		return new ResponseEntity<List<Rule>>(service.listAllRules(), HttpStatus.OK);
	}

	@SqsListener(value = "${cloud.aws.sqs.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void getMessage(final @NotificationMessage String message) throws JsonProcessingException {
		log.info("Queue name: " + QUEUE);

		if (message != null) {
			log.info("Message received : {} ", message);

		} else
			log.info("Null message detected");
	}
}
