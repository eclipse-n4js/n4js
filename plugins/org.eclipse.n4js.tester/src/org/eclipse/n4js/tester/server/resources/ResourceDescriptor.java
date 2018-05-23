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

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newEnumSet;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableCollection;
import static java.util.regex.Pattern.compile;

import java.util.Collection;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

/**
 * Representation of RESTful resource descriptor.
 */
public class ResourceDescriptor {

	private final Class<? extends BaseResource> clazz;
	private final Pattern pathPattern;
	private final Collection<HttpMethod> methods;
	private final Collection<String> requestContentType;
	private final String responseContentType;

	/**
	 * Creates a new resource descriptor instance with the given {@link Resource resource properties} and the associated
	 * class.
	 *
	 * @param resource
	 *            the resource for describing the properties of the RESTful resource.
	 * @param clazz
	 *            the class that is responsible for the resource business logic.
	 */
	public ResourceDescriptor(final Resource resource, final Class<? extends BaseResource> clazz) {
		this.clazz = clazz;
		pathPattern = compilePath(resource.path());
		methods = unmodifiableCollection(newEnumSet(newArrayList(resource.method()), HttpMethod.class));
		requestContentType = unmodifiableCollection(newHashSet(transform(newArrayList(resource.requestContentType()),
				input -> input.toString())));
		responseContentType = resource.responseContentType();
	}

	/**
	 * Returns with class associated with the resource.
	 *
	 * @return the class for the resource.
	 */
	public Class<? extends BaseResource> getClazz() {
		return clazz;
	}

	/**
	 * Validates the path info and returns with {@code true} if the servlet can process the path imitating the existence
	 * of the resource. Otherwise returns with {@code false}.
	 * <p>
	 * Since path parameters are not available like in <a href="https://jsr311.java.net/">JSR-311</a> for RESTful
	 * services implemented with servlets all request paths are accepted and after that it's the servlet's
	 * responsibility to parse the path and validate it. If the path info is not valid then the response code will be
	 * set to {@link HttpServletResponse#SC_NOT_FOUND HTTP 404}.
	 * <p>
	 * Assuming {@code some/context/root/} context path and {@code some/context/root/some/info/} request URI the path
	 * info will be {@code /some/info/} If the request URI is {@code some/context/root} then the path info will be an
	 * empty string but never {@code null}. In case of a {@code some/context/root/} request URI the path info will be
	 * {@code /}.
	 *
	 * @param pathInfo
	 *            the path info to validate. Never {@code null}.
	 * @return {@code true} if the path info matches for the servlet, otherwise {@code false}. In case of returning
	 *         false
	 */
	public boolean matchesWithPathInfo(final String pathInfo) {
		return pathPattern.matcher(pathInfo).matches();
	}

	/**
	 * Returns with a collection of HTTP methods that are accepted by the described RESTful resource.
	 *
	 * @return a collection of accepted HTTP methods.
	 */
	public Collection<HttpMethod> getMethods() {
		return methods;
	}

	/**
	 * Returns with a collection of allowed HTTP request content types.
	 *
	 * @return a collection of allowed HTTP request content types.
	 */
	public Collection<String> getRequestContentType() {
		return requestContentType;
	}

	/**
	 * Returns with the HTTP response content type for the RESTful resource represented by the current instance.
	 *
	 * @return the HTTP response content type.
	 */
	public String getResponseContentType() {
		return responseContentType;
	}

	private Pattern compilePath(final String path) {
		String s = path;
		if (s.lastIndexOf("/") == s.length() - 1) {
			s = s.substring(0, s.lastIndexOf("/"));
		}
		if (s.indexOf("/") == 0) {
			s = s.substring(1);
		}
		s = s.replaceAll("[{][^ /]+[}]", "[^ /]+");
		return compile(new StringBuilder("^/").append(s).append("[/]?$").toString());
	}

}
