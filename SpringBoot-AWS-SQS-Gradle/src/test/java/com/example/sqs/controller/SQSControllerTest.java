package com.example.sqs.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class SQSControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(SQSControllerTest.class);
    @Autowired
    @InjectMocks
    private SQSController sqsController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sqsController = new SQSController();
    }

    @Test
    public void sendMessageToUriTest() {
        sqsController.sendMessageToUri();
        LOG.info("Send message successfully");
    }

    @Test
    public void sendMessageToQueueTest() {
        sqsController.sendMessageToQueue("Test Amazon Sqs Queue");
        LOG.info("Send message successfully");
    }

    @Test
    public void getMessage() {
        sqsController.getMessage("Received all test Amazon Sqs Queue");
    }
}