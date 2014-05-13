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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bruno Farache
 */
public class LiferayProducer extends DefaultProducer {

	public LiferayProducer(LiferayEndpoint endpoint) {
		super(endpoint);
	}

	public LiferayEndpoint getEndpoint() {
		return (LiferayEndpoint)super.getEndpoint();
	}

	public void process(Exchange exchange) throws Exception {
		Object payload = exchange.getIn().getBody();

		if (_log.isDebugEnabled()) {
			_log.debug("LiferayProducer.process " + payload);
		}

		LiferayEndpoint endpoint = getEndpoint();
		MessageBus messageBus = endpoint.getMessageBus();
		String destinationName = endpoint.getDestinationName();

		Message message = new Message();
		message.setPayload(payload);

		messageBus.sendMessage(destinationName, message);
	}

	private final Logger _log = LoggerFactory.getLogger(LiferayProducer.class);

}