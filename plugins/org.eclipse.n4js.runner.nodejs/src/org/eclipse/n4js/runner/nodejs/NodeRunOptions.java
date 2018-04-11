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
package org.eclipse.n4js.runner.nodejs;

import static com.google.common.base.Strings.nullToEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.runner.SystemLoaderInfo;

import com.google.common.base.CharMatcher;

/**
 * Run options for node. Unlike configuration that is stored, this is just group of runtime options that are passed to
 * single node execution (as command line parameters or environmental variables). These are not persisted, but usually
 * are created from data in stored configurations and runtime data provided by user (e.g. file selection).
 */
public class NodeRunOptions {

	private String data = "{}";
	private String mod = "dummy module";
	private String paths = "./";
	private final List<String> initModules = new ArrayList<>();
	private String customEnginePath;
	private String engineOptions;
	private final Map<String, String> environmentVariables = new LinkedHashMap<>();
	private SystemLoaderInfo systemLoader = null;

	/** set name of the module to run. */
	public void setExecModule(String mod) {
		this.mod = mod;
	}

	/**
	 * get string representing execution data. This should contain user selection (e.g. module to run or computed list
	 * of tests) and be picked up (push or pull - details are contract between concrete runner/tester and supported
	 * runtime environments) by execution module.
	 *
	 * @returns string representing the module - project relative path to compiled module.
	 */
	public String getExecutionData() {
		return this.data;
	}

	/** set string representing execution data */
	public void setExecutionData(String data) {
		this.data = data;
	}

	/**
	 * get name of the module to run (execModule). This should be entry point to given project, e.g. Main module in
	 * application.
	 *
	 * @returns string representing the module - project relative path to compiled module.
	 */
	public String getExecModule() {
		return this.mod;
	}

	/** set string representing dependencies paths */
	public void setCoreProjectPaths(String paths) {
		this.paths = paths;
	}

	/**
	 * String representing paths that runner should scan for modules. Paths should be absolute and separated with
	 * appropriate separator. This value is used to set NODE_PATH environmental variable.
	 *
	 * @see <a href="http://nodejs.org/api/modules.html#modules_all_together">node require</a>
	 * @see <a href="https://gist.github.com/branneman/8048520#4-the-environment">better local require</a>
	 * @returns
	 */
	public String getCoreProjectPaths() {
		return this.paths;
	}

	/**
	 * get list of initialization modules (initModules) that have to be loaded before loading module to run
	 */
	public List<String> getInitModules() {
		return new ArrayList<>(this.initModules);
	}

	/** set list of modules to bootstrap before exec module */
	public void addInitModules(List<String> modules) {
		this.initModules.addAll(modules);
	}

	/**
	 * Returns with the custom execution engine path that should be appended to the path that is calculated from the
	 * dependencies defined in the N4JS manifest. Clients should be aware that the given path will be used as is. So for
	 * instance in case of NodeJs the path should be given as:
	 *
	 * <pre>
	 * some/custom/node/path:yet/another/custom/node/path
	 * </pre>
	 *
	 * then the {@code NODE_PATH} will be assembled in such a way:
	 *
	 * <pre>
	 * NODE_PATH=automatically/calculated/path:another/path/from/the/dependencies:some/custom/node/path:yet/another/custom/node/path
	 * </pre>
	 *
	 * @return the custom node path. Never {@code null}.
	 */
	public String getCustomEnginePath() {
		return nullToEmpty(customEnginePath);
	}

	/**
	 * Counterpart of {@link #getCustomEnginePath()}.
	 *
	 * @param customEnginePath
	 *            the custom execution engine path. Can be {@code null}, such cases, it will be ignored.
	 */
	public void setCustomEnginePath(final String customEnginePath) {
		this.customEnginePath = nullToEmpty(customEnginePath);
	}

	/**
	 * Returns with the custom options for the execution engine. The options given as a string will be used as is. It is
	 * the clients responsibility to use the proper formatting. For instance if the options should be given as a key
	 * value pairs, the it should be stored as
	 *
	 * <pre>
	 * someKey=someValue anotherKey=anotherValue
	 * </pre>
	 *
	 * but if the options are for instance NodeJs options, it should be provided as
	 *
	 * <pre>
	 * --harmony --verbose --etc
	 * </pre>
	 *
	 * furthermore all options should be separated with a {@link CharMatcher#BREAKING_WHITESPACE breaking whitespace}
	 * character.
	 *
	 * @return the execution engine options as a string.
	 */
	public String getEngineOptions() {
		return nullToEmpty(engineOptions);
	}

	/**
	 * Counterpart of the {@link #getEngineOptions()}.
	 *
	 * @param engineOptions
	 *            the new value to be set. Optional. If {@code null}, empty string will be used instead.
	 */
	public void setEngineOptions(final String engineOptions) {
		this.engineOptions = nullToEmpty(engineOptions);
	}

	/**
	 * Returns unmodifiable map of environment variables.
	 */
	public Map<String, String> getEnvironmentVariables() {
		return Collections.unmodifiableMap(environmentVariables);
	}

	/**
	 * Counterpart of the {@link #getEnvironmentVariables()}.
	 *
	 * @param environmentVariables
	 *            the new values to be set. The map will be copied to internal map.
	 */
	public void setEnvironmentVariables(Map<String, String> environmentVariables) {
		this.environmentVariables.clear();
		if (environmentVariables != null) {
			this.environmentVariables.putAll(environmentVariables);
		}
	}

	/**
	 * @return null or the SystemLoaderInfo to use.
	 */
	public SystemLoaderInfo getSystemLoader() {
		return systemLoader;
	}

	/**
	 *
	 * @param systemLoader
	 *            {@code nullable} system loader to use.
	 */
	public void setSystemLoader(SystemLoaderInfo systemLoader) {
		this.systemLoader = systemLoader;
	}

}
