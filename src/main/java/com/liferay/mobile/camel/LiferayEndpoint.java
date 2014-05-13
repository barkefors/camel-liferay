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

import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.SynchronousDestination;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * @author Bruno Farache
 */
public class LiferayEndpoint extends DefaultEndpoint {

	public LiferayEndpoint() {
	}

	public LiferayEndpoint(String uri) {
		super(uri);
	}

	public LiferayEndpoint(
		String uri, LiferayComponent component, String destinationName) {

		super(uri, component);

		_destinationName = destinationName;
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		return new LiferayConsumer(this, processor);
	}

	@Override
	public Producer createProducer() throws Exception {
		return new LiferayProducer(this);
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public MessageBus getMessageBus() {
		return ((LiferayComponent)getComponent()).getMessageBus();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	protected void addDefaultDestination() {
		MessageBus messageBus = getMessageBus();
		String destinationName = getDestinationName();

		if (messageBus.hasDestination(destinationName)) {
			return;
		}

		SynchronousDestination destination = new SynchronousDestination();
		destination.setName(destinationName);
		messageBus.addDestination(destination);
	}

	private String _destinationName;

}