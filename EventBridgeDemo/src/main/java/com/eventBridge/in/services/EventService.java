package com.eventBridge.in.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eventBridge.in.model.Input;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.EventBridgeException;
import software.amazon.awssdk.services.eventbridge.model.ListRulesRequest;
import software.amazon.awssdk.services.eventbridge.model.ListRulesResponse;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResultEntry;
import software.amazon.awssdk.services.eventbridge.model.Rule;

@Slf4j
@Service
public class EventService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private EventBridgeClient client;

	@Value("${cloud.aws.eventbridge.source}")
	private String source;

	@Value("${cloud.aws.eventbridge.detail-type}")
	private String detailType;

	@Value("${cloud.aws.eventbridge.bus-name}")
	private String busName;

	public void sendEvent(Input user) {
		String detailMessage = getUserToString(user);

		PutEventsRequestEntry requestEntry = PutEventsRequestEntry.builder().source(source).detailType(detailType)
				.detail(detailMessage).eventBusName(busName).build();

		List<PutEventsRequestEntry> requestEntries = new ArrayList<PutEventsRequestEntry>();
		requestEntries.add(requestEntry);

		PutEventsRequest eventsRequest = PutEventsRequest.builder().entries(requestEntry).build();
		log.info("Event Request: {}", eventsRequest);

		PutEventsResponse result = client.putEvents(eventsRequest);

		for (PutEventsResultEntry resultEntry : result.entries()) {
			if (resultEntry.eventId() != null) {
				log.info("Event Id: {}", resultEntry.eventId());
			} else {
				log.error("Injection failed with Error Code: {}", resultEntry.errorCode());
			}
		}

	}

	public List<Rule> listAllRules() {
		List<Rule> rules = new ArrayList<>();
		//enable fields  visibility for Rule
		mapper.setVisibility(mapper.getVisibilityChecker()
	             .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		try {
			ListRulesRequest rulesRequest = ListRulesRequest.builder()
					.eventBusName(busName).limit(10).build();

			ListRulesResponse response = client.listRules(rulesRequest);
			rules = response.rules();

			for (Rule rule : rules) {
				log.info("The rule name is : {}", rule.name());
				log.info("The rule ARN is : {}", rule.arn());
			}
		} catch (EventBridgeException e) {
			log.error(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		
		return rules;

	}

	public String getUserToString(Input user) {
		String detailMessage = null;
		try {
			detailMessage = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			log.error("Exception: {}", e);
		}
		return detailMessage;
	}
	
}
