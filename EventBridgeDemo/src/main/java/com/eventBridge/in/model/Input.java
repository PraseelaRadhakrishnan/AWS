package com.eventBridge.in.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Input {

	/*
	 * private String correlationId; private String customerName; private String
	 * orderNumber; private String warehouse;
	 */
	
	private String name;
	private String place;
	private int age;
}
