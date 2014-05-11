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

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

/**
 * @author Bruno Farache
 */
public class LiferayConsumer extends ScheduledPollConsumer {

	public LiferayConsumer(LiferayEndpoint endpoint, Processor processor) {
		super(endpoint, processor);

		_endpoint = endpoint;
	}

	@Override
	protected int poll() throws Exception {
		Exchange exchange = _endpoint.createExchange();

		Date now = new Date();
		exchange.getIn().setBody("Hello World! The time is " + now);

		try {
			getProcessor().process(exchange);

			return 1;
		}
		finally {
			Exception exception = exchange.getException();

			if (exception != null) {
				getExceptionHandler().handleException(
					"Error processing exchange", exchange, exception);
			}
		}
	}

	private LiferayEndpoint _endpoint;

}