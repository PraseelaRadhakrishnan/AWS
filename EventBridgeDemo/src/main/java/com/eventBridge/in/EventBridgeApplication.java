package com.eventBridge.in;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;

@SpringBootApplication(exclude = ContextInstanceDataAutoConfiguration.class)
public class EventBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventBridgeApplication.class, args);
	}
}
