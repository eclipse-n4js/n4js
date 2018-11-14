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

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Builder for instantiating {@link ServletHolder servlet holder} instances that can create servlets with injected
 * fields by Guice.
 */
@Singleton
public class ServletHolderBuilder {
	private static final Logger LOGGER = Logger.getLogger(ServletHolderBuilder.class);

	@Inject
	private Injector injectedInjector;

	/**
	 * Creates a new Guice aware {@link ServletHolder servlet holder} instance.
	 *
	 * @param clazz
	 *            the class of the servlet to instantiate.
	 * @return the Guice aware servlet holder.
	 */
	public ServletHolder build(final Class<? extends Servlet> clazz) {
		ServletHolder servletHolder = new ServletHolder(clazz) {
			@Override
			protected Servlet newInstance() throws ServletException, IllegalAccessException, InstantiationException {
				try {
					Servlet servlet = super.newInstance();
					Injector injector = injectedInjector;
					injector.injectMembers(servlet);
					return servlet;
				} catch (Exception e) {
					LOGGER.error("Error while creating servlet for class: " + clazz + ";", e);
					throw new RuntimeException(e);
				}
			}
		};
		return servletHolder;
	}

}
