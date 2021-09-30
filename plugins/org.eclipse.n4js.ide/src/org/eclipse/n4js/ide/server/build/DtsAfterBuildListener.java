/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.build;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonModificationUtils;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.utils.JsonUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterBuildListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildResult;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Executed after a single project was build. Ensures that there exists
 * <ul>
 * <li/>a ts.config file in the project folder and that this file contains correct information.
 * </ul>
 */
public class DtsAfterBuildListener implements AfterBuildListener {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	final N4JSProjectConfigSnapshot projectConfig;
	final File tsconfig;

	/** Constructor */
	public DtsAfterBuildListener(N4JSProjectConfigSnapshot projectConfig) {
		this.projectConfig = projectConfig;
		tsconfig = projectConfig.getPathAsFileURI().toPath().resolve(N4JSGlobals.TS_CONFIG).toFile();
	}

	@Override
	public void afterBuild(XBuildRequest request, XBuildResult result) {
		try {
			if (request.canGenerate()) {
				ensureTSConfig();
			}
		} catch (Exception e) {
			LOGGER.error("Could not read tsconfig.json: " + tsconfig.toString(), e);
		}
	}

	private void ensureTSConfig() throws IOException {
		if (tsconfig.isFile()) {
			ensureContent();
		} else {
			createDefault();
		}
	}

	private void createDefault() throws IOException {
		String defaultTSConfig = "{\n"
				+ "    \"include\": [\"" + createIncludePath() + "\"],\n"
				+ "    \"exclude\": [\"node_modules\"],\n"
				+ "    \"compilerOptions\": {\n"
				+ "        \"target\": \"es5\",\n"
				+ "        \"lib\": " + createLibValue() + ",\n"
				+ "        \"module\": \"commonjs\",\n"
				+ "        \"noImplicitAny\": false\n"
				+ "    }\n"
				+ "}\n"
				+ "";

		Files.writeString(tsconfig.toPath(), defaultTSConfig);
	}

	private String createLibValue() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("\"es2019\", \"es2020\"");
		for (ProjectReference requiredLibRef : projectConfig.getProjectDescription().getRequiredRuntimeLibraries()) {
			N4JSProjectName requiredLibName = requiredLibRef.getN4JSProjectName();
			ImmutableSet<String> dtsLibNames = N4JSGlobals.N4JS_DTS_LIB_CORRESPONDENCE.get(requiredLibName);
			for (String dtsLibName : dtsLibNames) {
				sb.append(", \"");
				sb.append(dtsLibName);
				sb.append('"');
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private String createIncludePath() {
		return projectConfig.getOutputPath() + "/**/*.ts";
	}

	private void ensureContent() throws IOException {
		JsonElement json = JsonUtils.loadJson(tsconfig.toPath());
		JsonArray includeArr = json.getAsJsonObject().getAsJsonArray("include");
		String includePath = createIncludePath();
		for (JsonElement includeElem : includeArr) {
			String includeValue = includeElem.getAsString();
			if (Objects.equals(includeValue, includePath)) {
				return; // everything is fine
			}
		}

		// add include path to an existing ts.config
		JsonArray addToArray = new JsonArray();
		addToArray.add(includePath);
		Map<String, JsonElement> elements = new HashMap<>();
		elements.put("include", addToArray);
		PackageJsonModificationUtils.addProperties(tsconfig, elements.entrySet());
	}

}
