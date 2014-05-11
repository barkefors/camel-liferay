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

	public LiferayEndpoint(String endpointUri) {
		super(endpointUri);
	}

	public LiferayEndpoint(String uri, LiferayComponent component) {
		super(uri, component);
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		return new LiferayConsumer(this, processor);
	}

	public Producer createProducer() throws Exception {
		return new LiferayProducer(this);
	}

	public boolean isSingleton() {
		return true;
	}

}