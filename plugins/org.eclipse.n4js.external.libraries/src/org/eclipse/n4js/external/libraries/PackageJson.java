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
package org.eclipse.n4js.external.libraries;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

import org.eclipse.n4js.utils.JsonPrettyPrinterFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// FIXME GH-809 remove this class
/**
 * POJO for the {@code package.json} file.
 */
@SuppressWarnings("javadoc")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PackageJson {

	/**
	 * The file name with the extension of the {@code package.json} file for npm.
	 */
	public final static String PACKAGE_JSON = "package.json";

	/**
	 * GHOLD-217: changed type from "Collection<Collection<String>>" to "Collection<Collection<Object>>", since
	 * https://github.com/npm/npm/blob/a3d718f5e4f15f0c2498e74304c979120611d67e/lib/fetch-package-metadata.js#L77
	 * introduces objects in the inner notation. This change went live with npm version 3.9.3
	 */
	public Collection<Collection<Object>> _args;

	public String _from;

	public String _id;

	public boolean _inCache;

	public String _location;

	public Person _npmUser;

	public String _npmVersion;

	public Map<String, Object> _phantomChildren;

	public Map<String, Object> _requested;

	public Collection<String> _requiredBy;

	public String _resolved;

	public String _shasum;

	public String _shrinkwrap;

	public String _spec;

	public String _where;

	public Person author;

	public Map<String, Object> bugs;

	public Collection<Person> contributors;

	public Map<String, String> dependencies;

	public String description;

	public Map<String, String> devDependencies;

	public Map<String, String> directories;

	public Map<String, String> dist;

	public Collection<String> files;

	public String gitHead;

	public String homepage;

	public boolean installable;

	public Collection<String> keywords;

	public Object license;

	public Collection<Person> maintainers;

	public String name;

	public Map<String, String> optionalDependencies;

	public Map<String, String> repository;

	public Map<String, String> scripts;

	public String version;

	public String main;

	@Override
	public String toString() {
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonPrettyPrinterFactory());
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			throw new RuntimeException("Error while serializing package.json: " + this, e);
		}
	}

	/**
	 * Reads and parses the {@code package.json} content and returns with an assembled instance representing the content
	 * of the file.
	 *
	 * @param packageLocation
	 *            the location of the {@code package.json} file.
	 *
	 * @return a new instance representing the content of the parsed file.
	 */
	static public PackageJson readValue(URI packageLocation) {
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonPrettyPrinterFactory());
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			return mapper.readValue(new File(packageLocation), PackageJson.class);
		} catch (Exception e) {
			throw new RuntimeException("Error while reading package.json from " + packageLocation + ".", e);
		}
	}

}
