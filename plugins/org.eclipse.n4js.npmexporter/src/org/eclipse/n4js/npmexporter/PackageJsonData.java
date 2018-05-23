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
package org.eclipse.n4js.npmexporter;

import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Jackson - DataObject. Used for serializing to Json.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PackageJsonData {

	@JsonProperty
	String name;

	@JsonProperty
	String version;

	@JsonProperty
	String description;

	@JsonProperty
	String main;

	@JsonProperty
	Scripts scripts;

	@JsonProperty
	String author;

	@JsonProperty
	String license;

	@JsonProperty
	LinkedHashMap<String, String> dependencies;

	@JsonProperty
	LinkedHashMap<String, String> devDependencies;

	@JsonProperty
	List<String> n4js_outputFolderContents;

	@JsonSerialize
	static class Scripts {
		@JsonProperty
		String test;
	}

	@JsonSerialize
	static class Dependency {
		@JsonProperty
		String name;

		@JsonProperty
		String version;
	}

}

/*-

 {
  "name": "x",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "",
  "license": "ISC"
}

 */
