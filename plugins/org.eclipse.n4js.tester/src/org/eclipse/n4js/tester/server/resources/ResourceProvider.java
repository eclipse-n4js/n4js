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
package org.eclipse.n4js.tester.server.resources;

import org.apache.log4j.Logger;

import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Class for providing RESTful resource instances for their classes.
 */
public class ResourceProvider {
	private static final Logger LOGGER = Logger.getLogger(ResourceProvider.class);

	@Inject
	private Injector injectedInjector;

	/**
	 * Creates a new resource instance given with the resource class.
	 *
	 * @param clazz
	 *            the class to instantiate.
	 * @return the new instance of the class.
	 */
	public BaseResource createResource(final Class<? extends BaseResource> clazz) {
		try {
			BaseResource resource = clazz.getConstructor().newInstance();
			Injector injector = injectedInjector;
			injector.injectMembers(resource);
			return resource;
		} catch (final Exception e) {
			LOGGER.error("Error while creating resource for class: " + clazz + ";", e);
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

}
