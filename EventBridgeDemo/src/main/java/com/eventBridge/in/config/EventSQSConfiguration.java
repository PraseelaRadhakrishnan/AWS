package com.eventBridge.in.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class EventSQSConfiguration {
	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	@Primary
	public AmazonSQSAsync amazonSqsAsync() {
		return AmazonSQSAsyncClientBuilder.standard().withRegion(region)
				.withCredentials((AWSCredentialsProvider) getCredentialsProvider()).build();
	}

	private AWSCredentialsProvider getCredentialsProvider() {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			return credentialsProvider;
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profile file. "
					+ "Please make sure that your credentials file is as the correct location "
					+ "(c:\\users\\your.name\\.aws\\credentials), and is in valid format", e);
		}
	}

}
