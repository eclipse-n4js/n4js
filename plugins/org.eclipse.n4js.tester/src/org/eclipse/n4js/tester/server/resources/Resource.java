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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

/**
 * Classes annotated with {@code @Resource} annotation will be considered by {@link ResourceRouterServlet resource
 * router servlet} as a possible target imitating a RESTful resource implemented via pure {@link HttpServlet HTTP
 * servlet}.
 */
@Documented
@Retention(RUNTIME)
@Target(value = TYPE)
public @interface Resource {

	/** The default content type. {@value} */
	public static final String APPLICATION_JSON = "application/json";

	/**
	 * The path for the RESTful resource.
	 * <p>
	 * By default path parameters are not available for RESTful resources implemented via {@link HttpServlet HTTP
	 * servlets} hence this path will be transformed to a regular expression than compiled to a {@link Pattern pattern}
	 * and the resource will be invoked if the request URI matches with the compiled pattern given with this path.
	 * <p>
	 * For instance the following resource path <tt>/{sessionId}/ping/</tt> will be transformed to the
	 * {@code ^/[^ /]+/ping[/]?$} pattern.
	 *
	 * @return the path for the resource.
	 */
	String path();

	/**
	 * The accepted HTTP request content types for the RESTful resource.
	 *
	 * @return an array of accepted request content types.
	 */
	ContentType[] requestContentType();

	/**
	 * The produced HTTP response content type by the RESTful resource. <br>
	 * By default it is {@value #APPLICATION_JSON}.
	 *
	 * @return the HTTP response content type.
	 */
	String responseContentType() default APPLICATION_JSON;

	/**
	 * An array of HTTP methods that annotated RESTful resource percepts.
	 *
	 * @return an array of percepted HTTP methods.
	 */
	HttpMethod[] method();

}
