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
package org.eclipse.n4js.tester.server.resources.service;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.eclipse.n4js.tester.server.resources.ContentType.ASSEMBLE_TEST_CATALOG;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.GET;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.tester.server.resources.BaseResource;
import org.eclipse.n4js.tester.server.resources.Resource;

import com.google.inject.Inject;

/**
 * RESTful resource for assembling and providing a test catalog based on the tests available in the {@link IN4JSCore
 * N4JS core} based workspace.
 *
 * The assembled catalog content depends on the built state of the workspace.
 */
@Resource(path = "/testcatalog/", method = GET, requestContentType = ASSEMBLE_TEST_CATALOG)
public class TestCatalogAssemblerResource extends BaseResource {

	@Inject
	private TestCatalogSupplier catalogSupplier;

	@Override
	protected int doHandle(final String body, final String pathInfo) throws ServletException {
		return SC_OK;
	}

	@Override
	protected void handleStatusOk(final HttpServletRequest req, final HttpServletResponse resp, String escapdPathInfo)
			throws ServletException {

		try {
			final String body = catalogSupplier.get();
			try (final OutputStream os = resp.getOutputStream();
					final OutputStreamWriter osw = new OutputStreamWriter(os)) {
				osw.write(body);
				osw.flush();
			}
		} catch (final Exception e) {
			final String msg = "Error while assembling test catalog for all tests.";
			LOGGER.error(msg, e);
			throw new ServletException(msg, e);
		}

	}

}
