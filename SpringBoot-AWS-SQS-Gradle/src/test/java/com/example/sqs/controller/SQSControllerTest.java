package com.example.sqs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


class SQSControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(SQSControllerTest.class);
    @Autowired
    @InjectMocks
    private SQSController sqsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sqsController = new SQSController();
    }

    @Test
    void sendMessageToUriTest() {
        sqsController.sendMessageToUri();
        LOG.info("Send message successfully");
    }

    @Test
    void sendMessageToQueueTest() {
        sqsController.sendMessageToQueue("Test Amazon Sqs Queue");
        LOG.info("Send message successfully");
    }

    @Test
    void getMessage() {
        sqsController.getMessage("Received all test Amazon Sqs Queue");
    }
}