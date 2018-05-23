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

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Splitter.on;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterables.tryFind;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.equalsWithMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.n4js.tester.server.resources.service.TestCatalogAssemblerResource;
import org.eclipse.n4js.tester.server.resources.sessions.EndSessionResource;
import org.eclipse.n4js.tester.server.resources.sessions.PingSessionResource;
import org.eclipse.n4js.tester.server.resources.sessions.StartSessionResource;
import org.eclipse.n4js.tester.server.resources.tests.EndTestResource;
import org.eclipse.n4js.tester.server.resources.tests.PingTestResource;
import org.eclipse.n4js.tester.server.resources.tests.StartTestResource;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

/**
 * HTTP servlet implementation for accepting all request URIs matching with the context root, then this class is
 * responsible for looking up the corresponding {@link BaseResource resource} implementation (if any) and delegate the
 * request/response handling to that resource.
 */
public class ResourceRouterServlet extends HttpServlet {

	private static final Logger LOGGER = getLogger(ResourceRouterServlet.class);

	/**
	 * Context path of the {@code start session} resource.
	 */
	public static final String CONTEXT_PATH = "/testing/sessions/";

	private static Collection<Class<?>> RESOURCE_CLASSES = unmodifiableCollection(asList(
			StartSessionResource.class,
			PingSessionResource.class,
			EndSessionResource.class,
			StartTestResource.class,
			PingTestResource.class,
			EndTestResource.class,
			TestCatalogAssemblerResource.class));

	//@formatter:off
	private static final Supplier<Iterable<ResourceDescriptor>> RESOURCE_DESCRIPTORS = memoize(new Supplier<Iterable<ResourceDescriptor>>() {
		@Override
		@SuppressWarnings("unchecked")
		public Iterable<ResourceDescriptor> get() {
			return transform(
					filter(RESOURCE_CLASSES,
							clazz -> BaseResource.class.isAssignableFrom(clazz)
							&& clazz.isAnnotationPresent(Resource.class)), clazz -> new ResourceDescriptor(
									clazz.getAnnotation(Resource.class),
									(Class<? extends BaseResource>) clazz));
		}
	});
	// @formatter:on

	@Inject
	private ResourceProvider resourceProvider;

	@Override
	protected void service(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		// Make sure that we do not un-escape the pathInfo here since otherwise FQNs might
		// be mistaken for URI segments
		String pathInfo = getEscapedPathInfo(req);

		// try to find a matching resource
		final Optional<ResourceDescriptor> opt = tryFind(RESOURCE_DESCRIPTORS.get(),
				desc -> desc.matchesWithPathInfo(pathInfo));

		// HTTP 404
		if (!opt.isPresent()) {
			resp.setStatus(SC_NOT_FOUND);
			return;
		}

		// HTTP 405
		final ResourceDescriptor desc = opt.get();
		if (!any(desc.getMethods(), method -> equalsWithMethod(method, req))) {
			resp.setStatus(SC_METHOD_NOT_ALLOWED);
			return;
		}

		// HTTP 415
		final Collection<String> contentType = desc.getRequestContentType();
		if (!any(on(";").trimResults().split(nullToEmpty(req.getContentType())), in(contentType))) {
			resp.setStatus(SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}
		try {
			final BaseResource resource = resourceProvider.createResource(desc.getClazz());
			resource.doHandle(req, resp, pathInfo);
			resp.setContentType(desc.getResponseContentType());
			if (SC_OK == resp.getStatus()) {
				resource.handleStatusOk(req, resp, pathInfo);
			}
		} catch (final ClientResourceException e) {
			resp.reset();
			resp.setStatus(e.getStatusCode());
		} catch (final Exception e) {
			LOGGER.error("Unexpected error while trying to serve request.\nPath info: '" + req.getPathInfo() + "'.", e);
		}
	}

	/**
	 * Returns the un-escaped path info of the given request.
	 */
	private String getEscapedPathInfo(final HttpServletRequest req) throws ServletException {
		// we need to recover the escaped path info manually, since req.getPathInfo() is un-escaped automatically.
		URI escapedPathInfoURI;
		try {
			escapedPathInfoURI = new URI(req.getContextPath() + CONTEXT_PATH).relativize(
					new URI(req.getRequestURI()));
		} catch (URISyntaxException e) {
			throw new ServletException("Failed to extract un-escaped path info from request.", e);
		}

		return "/" + escapedPathInfoURI.toString();
	}

}
