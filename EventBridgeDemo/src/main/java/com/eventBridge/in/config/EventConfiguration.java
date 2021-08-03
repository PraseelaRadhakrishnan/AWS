package com.eventBridge.in.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.AmazonClientException;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

@Configuration
public class EventConfiguration {
	@Value("${cloud.aws.region.static}")
	private Region awsRegion;

	@Primary
	@Bean
	public EventBridgeClient bridgeClient() {
		return EventBridgeClient.builder().region(awsRegion).credentialsProvider(getCredentialsProvider()).build();
	}

	private AwsCredentialsProvider getCredentialsProvider() {
		ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder().build();
		try {
			return credentialsProvider;
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profile file. "
					+ "Please make sure that your credentials file is as the correct location "
					+ "(c:\\users\\your.name\\.aws\\credentials), and is in valid format", e);
		}
	}
}
