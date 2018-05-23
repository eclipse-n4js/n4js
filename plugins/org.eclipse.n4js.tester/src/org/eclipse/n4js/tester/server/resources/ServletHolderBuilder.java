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

	@Inject
	private Injector injector;

	/**
	 * Creates a new Guice aware {@link ServletHolder servlet holder} instance.
	 *
	 * @param clazz
	 *            the class of the servlet to instantiate.
	 * @return the Guice aware servlet holder.
	 */
	public ServletHolder build(final Class<? extends Servlet> clazz) {
		return new ServletHolder(clazz) {
			@Override
			protected Servlet newInstance() throws ServletException, IllegalAccessException, InstantiationException {
				final Servlet servlet = super.newInstance();
				injector.injectMembers(servlet);
				return servlet;
			}
		};
	}

}
