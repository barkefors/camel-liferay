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
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.SynchronousDestination;

import org.apache.camel.CamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;

/**
 * @author Bruno Farache
 */
public abstract class BaseTest extends CamelTestSupport {

	protected void addDestination() {
		if (messageBus.hasDestination(destinationName)) {
			return;
		}

		SynchronousDestination destination = new SynchronousDestination();
		destination.setName(destinationName);
		messageBus.addDestination(destination);
	}

	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext context = super.createCamelContext();

		messageBus = new DefaultMessageBus();
		addDestination();

		LiferayComponent component = context.getComponent(
			"liferay", LiferayComponent.class);

		component.setMessageBus(messageBus);

		return context;
	}

	protected String destinationName = "destination";
	protected MessageBus messageBus;

}