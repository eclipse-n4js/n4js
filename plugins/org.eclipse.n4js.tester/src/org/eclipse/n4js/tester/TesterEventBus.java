/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester;

import static org.apache.log4j.Logger.getLogger;

import org.apache.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.inject.Singleton;

/**
 * Asynchronous event bus implementation for the N4 tester component.
 */
@Singleton
public class TesterEventBus extends EventBus {

	private static final Logger LOGGER = getLogger(TesterEventBus.class);

	/**
	 * Sole constructor for the event tester event bus.
	 */
	public TesterEventBus() {
		super("Tester event bus");
	}

	@Override
	public void register(final Object object) {
		super.register(object);
		LOGGER.info("Registered: " + object);
	}

	@Override
	public void unregister(Object object) {
		super.unregister(object);
		LOGGER.info("Unregistered: " + object);
	}

}
