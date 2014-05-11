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

package com.liferay.mobile;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.SynchronousDestination;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bruno Farache
 */
public class LiferayConsumer extends DefaultConsumer
	implements MessageListener {

	public LiferayConsumer(LiferayEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	public LiferayEndpoint getEndpoint() {
		return (LiferayEndpoint)super.getEndpoint();
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		Exchange exchange = getEndpoint().createExchange();
		Object payload = message.getPayload();

		if (_log.isDebugEnabled()) {
			_log.debug("LiferayConsumer.receive " + payload);
		}

		exchange.getIn().setBody(payload);

		try {
			getProcessor().process(exchange);
		}
		catch (Exception e) {
			_log.error("Error processing message", e);
		}
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();

		LiferayEndpoint endpoint = getEndpoint();

		String destination = endpoint.getDestination();
		MessageBus messageBus = endpoint.getMessageBus();

		if (!messageBus.hasDestination(destination)) {
			SynchronousDestination synchronousDestination =
				new SynchronousDestination();

			synchronousDestination.setName(destination);

			messageBus.addDestination(synchronousDestination);
		}

		messageBus.registerMessageListener(destination, this);
	}

	private final Logger _log = LoggerFactory.getLogger(LiferayConsumer.class);

}