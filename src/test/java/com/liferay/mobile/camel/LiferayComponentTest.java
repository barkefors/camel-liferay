/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.camel;

import com.liferay.portal.kernel.messaging.DefaultMessageBus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;

import org.junit.Test;

/**
 * @author Bruno Farache
 */
public class LiferayComponentTest extends CamelTestSupport {

	@Test
	public void sendPayload() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);

		Message message = new Message();
		message.setPayload("payload");
		_messageBus.sendMessage("destination", message);

		assertMockEndpointsSatisfied();
	}

	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext context = super.createCamelContext();

		_messageBus = new DefaultMessageBus();

		LiferayComponent component = context.getComponent(
			"liferay", LiferayComponent.class);

		component.setMessageBus(_messageBus);

		return context;
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {

			public void configure() {
				from("liferay:destination").to("liferay://bar").to(
					"mock:result");
			}

		};
	}

	private MessageBus _messageBus;

}