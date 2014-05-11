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

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * @author Bruno Farache
 */
public class LiferayComponent extends DefaultComponent {

	public MessageBus getMessageBus() {
		return _messageBus;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public MessageBus _messageBus;

	@Override
	protected Endpoint createEndpoint(
			String uri, String remaining, Map<String, Object> parameters)
		throws Exception {

		Endpoint endpoint = new LiferayEndpoint(uri, this, remaining);
		setProperties(endpoint, parameters);

		return endpoint;
	}

}