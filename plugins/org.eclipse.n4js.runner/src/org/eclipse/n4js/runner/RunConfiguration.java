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
package org.eclipse.n4js.runner;

import static com.google.common.base.Strings.nullToEmpty;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;

import com.google.common.base.CharMatcher;

/**
 * Container for all configuration attributes required for a single execution of some N4JS code.
 * <p>
 * There are different kinds of attributes managed by a run configuration:
 * <ul>
 * <li><u>primary vs. derived attributes</u><br>
 * <em>Primary</em> values have to be provided from the outside in one way or another (usually by the user) whereas
 * <em>derived</em> values are computed based on one or more of the primary values.
 * <li><u>persistent vs. volatile attributes</u><br>
 * In some situations, run configurations are stored for later use; in this case, <em>persistent</em> values will be
 * stored whereas <em>volatile</em> values will be thrown away and re-computed later when the run configuration is
 * restored.
 * </ul>
 * Subclasses may introduce additional, runner-specific configuration values. In this case, methods
 * {@link #readPersistentValues()} and {@link #writePersistentValues(Map)} have to be extended to handle all additional
 * <em>persistent</em> values and method {@link IRunner#prepareConfiguration(RunConfiguration) prepareConfiguration()}
 * of the corresponding {@link IRunner} implementation must be implemented to compute all additional <em>derived</em>
 * values.
 * <p>
 * By default, the only primary and persisted values are {@link #getName() name}, {@link #getRunnerId() runnerId},
 * {@link #getImplementationId() implementationId}, and {@link #getUserSelection() userSelection}; all other values
 * defined in this base class are derived and volatile.
 * <p>
 * Attribute {@link #additionalPaths} is primary and volatile, because it is only used for run configurations that are
 * created internally and never sent to Eclipse's run configuration framework. This is currently only by
 * {@code doCompileAndExecute} of class {@code XpectN4JSES5TranspilerHelper} to for {@code quickfixAndRun} Xpect test
 * method.
 * <p>
 * A run configuration may be re-launched at a later point in time. So all values stored in a run configuration must be
 * independent of temporary folders and files!
 */
public class RunConfiguration {

	/** Key used for attribute specifying the name. */
	public final static String NAME = "NAME";

	/** Key used for attribute specifying the runner ID. */
	public final static String RUNNER_ID = "RUNNER_ID";

	/** Key used for attribute specifying the runtime environment. */
	public final static String RUNTIME_ENVIRONMENT = "RUNTIME_ENVIRONMENT";

	/** Key used for attribute specifying user selection */
	public final static String USER_SELECTION = "USER_SELECTION";

	/** Key used for attribute specifying the implementation ID. */
	public final static String IMPLEMENTATION_ID = "IMPLEMENTATION_ID";

	/** Key used for attribute specifying the working directory of the spawned process. */
	public final static String WORKING_DIRECTORY = "WORKING_DIRECTORY";

	/** Key used for attribute specifying the .js file to execute. */
	public final static String FILE_TO_RUN = "FILE_TO_RUN";

	/** Key used for attribute specifying the custom execution {@link #getEngineOptions() engine options}. */
	public final static String ENGINE_OPTIONS = "ENGINE_OPTIONS";

	/** Key used for attribute specifying the custom {@link #getRunOptions() run options}. */
	public final static String RUN_OPTIONS = "RUN_OPTIONS";

	/** Key used for custom environment variables. */
	public final static String ENV_VARS = "ENV_VARS";

	/** Within the execution data passed to the exec module, this key is used to hold the user selection. */
	public final static String EXEC_DATA_KEY__USER_SELECTION = "userSelection";

	/** Within the execution data passed to the exec module, this key is used to hold the API/impl project mapping. */
	public final static String EXEC_DATA_KEY__PROJECT_NAME_MAPPING = "projectNameMapping";

	private String name;

	private String runnerId;

	private String engineOptions;

	private final Map<String, Object> executionData = new LinkedHashMap<>();

	private RuntimeEnvironment runtimeEnvironment;

	private N4JSProjectName implementationId;

	private final Map<N4JSProjectName, N4JSProjectName> apiImplProjectMapping = new LinkedHashMap<>();

	/**
	 * See {@link #getUserSelection()} for details.
	 */
	private URI userSelection;

	private Path workingDirectory;

	private Path fileToRun;

	private String runOptions;

	private final Map<String, String> environmentVariables = new LinkedHashMap<>();

	private final Map<Path, N4JSProjectName> coreProjectPaths = new LinkedHashMap<>();

	private final LinkedHashSet<String> additionalPaths = new LinkedHashSet<>();

	/**
	 * Additional path to be added to NODE_PATH if needed
	 */
	public Collection<String> getAdditionalPaths() {
		return additionalPaths;
	}

	/** @see #getAdditionalPaths() */
	public void addAdditionalPath(String file) {
		this.additionalPaths.add(file);
	}

	/** @see #getAdditionalPaths() */
	public void addAdditionalPath(Collection<String> files) {
		this.additionalPaths.addAll(files);
	}

	/**
	 * Human-readable name of the run configuration.
	 */
	public String getName() {
		return name;
	}

	/** @see #getName() */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Custom options passed to the execution engine (e.g. node.js), not the executed N4JS code (compare to
	 * {@link #getRunOptions() run options}). The format of this string depends on the engine being used, but in any
	 * case options should be separated with a {@link CharMatcher#BREAKING_WHITESPACE breaking whitespace} character.
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
	 * Identifier of the runner to use.
	 */
	public String getRunnerId() {
		return runnerId;
	}

	/** @see #getRunnerId() */
	public void setRunnerId(String runnerId) {
		this.runnerId = runnerId;
	}

	/**
	 * The runtime environment to use.
	 */
	public RuntimeEnvironment getRuntimeEnvironment() {
		return runtimeEnvironment;
	}

	/** @see #getRuntimeEnvironment() */
	public void setRuntimeEnvironment(RuntimeEnvironment runtimeEnvironment) {
		this.runtimeEnvironment = runtimeEnvironment;
	}

	/**
	 * User selection defined by a URI as used by {@link IN4JSCore}, {@link IN4JSProject}, etc. In the headless case
	 * this will be a file URI, in the UI case it will be a platform resource URI.
	 * <p>
	 * Runners will expect the URI of a file, testers can cope with URIs of methods, classes, files, folders, projects.
	 * <p>
	 * The user selection may be accompanied with a test selection in TestConfiguration.
	 */
	public URI getUserSelection() {
		return userSelection;
	}

	/** @see #getUserSelection() */
	public void setUserSelection(URI userSelection) {
		this.userSelection = userSelection;
	}

	/**
	 * The {@link ProcessBuilder#directory(java.io.File) working directory} of the process spawned by the runner.
	 */
	public Path getWorkingDirectory() {
		return workingDirectory;
	}

	/** @see #getWorkingDirectory() */
	public void setWorkingDirectory(Path workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * The Javascript file to execute, either as an absolute path or a path relative to the
	 * {@link #getWorkingDirectory() working directory}. Usually derived from the {@link #getUserSelection() user
	 * selection} (i.e. file to run will be the file selected by the user), but in some cases the file to run will be
	 * special, e.g. testers will set this to the main .js file in "n4js-mangelhaft-cli".
	 */
	public Path getFileToRun() {
		return fileToRun;
	}

	/** @see #getFileToRun() */
	public void setFileToRun(Path fileToRun) {
		this.fileToRun = fileToRun;
	}

	/**
	 * Custom options passed to the executed N4JS code, not the execution engine (e.g. node.js; compare to
	 * {@link #getEngineOptions() engine options}). The format of this string depends on the code being executed, but in
	 * any case options should be separated with a {@link CharMatcher#BREAKING_WHITESPACE breaking whitespace}
	 * character.
	 *
	 * @return the run options as a string.
	 */
	public String getRunOptions() {
		return nullToEmpty(runOptions);
	}

	/** @see #getRunOptions() */
	public void setRunOptions(final String runOptions) {
		this.runOptions = nullToEmpty(runOptions);
	}

	/**
	 * The ID of the implementation to use or <code>null</code> if no implementation is selected. Corresponds to the
	 * value given in an implementation project's manifest file via property '<code>ImplementationId</code>'.
	 * <p>
	 * This is relevant only if there are one or more API projects among the direct or indirect dependencies of the
	 * module to run. In that case, this value will determine which implementation to use. If this value is
	 * <code>null</code> and ...
	 * <ul>
	 * <li>only a single implementation exists in the workspace, then that implementation should be used;
	 * <li>several implementations exist, an error is to be shown (headless case) or a dialog should be shown with a
	 * list of available implementation IDs (UI case).
	 * </ul>
	 */
	public N4JSProjectName getImplementationId() {
		return implementationId;
	}

	/** @see #getImplementationId() */
	public void setImplementationId(N4JSProjectName implementationId) {
		this.implementationId = implementationId;
	}

	/**
	 * For each API project in the direct and indirect project dependencies of the module to run, this will contain a
	 * mapping from the <code>projectName</code> of the API project to the <code>projectName</code> of the
	 * implementation project to use in the run. Never returns <code>null</code> but may return an empty map.
	 */
	public Map<N4JSProjectName, N4JSProjectName> getApiImplProjectMapping() {
		return Collections.unmodifiableMap(apiImplProjectMapping);
	}

	/** @see #getApiImplProjectMapping() */
	public void setApiImplProjectMapping(Map<N4JSProjectName, N4JSProjectName> apiImplProjectMapping) {
		this.apiImplProjectMapping.clear();
		this.apiImplProjectMapping.putAll(apiImplProjectMapping);
	}

	/** @see #getApiImplProjectMapping() */
	public void setApiImplProjectMappingFromProjects(Map<IN4JSProject, IN4JSProject> apiImplProjectMapping) {
		this.apiImplProjectMapping.clear();
		apiImplProjectMapping.entrySet().forEach(
				e -> this.apiImplProjectMapping.put(e.getKey().getProjectName(), e.getValue().getProjectName()));
	}

	/**
	 * Execution data, derived from user selection and passed on to the executed code, i.e. the low-level start-up code
	 * defined in the runtime environment.
	 */
	public Map<String, Object> getExecutionData() {
		return executionData;
	}

	/** @see #getExecutionData() */
	public void setExecutionData(Map<String, Object> data) {
		this.executionData.clear();
		this.executionData.putAll(data);
	}

	/**
	 * Convenience method. Same as {@link #getExecutionData()}, but returns the map of key/value pairs as a JSON string.
	 */
	public String getExecutionDataAsJSON() {
		return JSON.toString(this.executionData);
	}

	/**
	 * Convenience method. Sets a single key/value pair in the execution data without changing any values for other
	 * keys.
	 */
	public void setExecutionData(String key, Object value) {
		this.executionData.put(key, value);
	}

	/**
	 * List of absolute file system paths to output folders containing the compiled code of all required projects (i.e.
	 * of project containing the userSelection, its direct and indirect dependencies, the runtime environment project).
	 * These are the <code>.../src-gen/es5/</code> folders.
	 */
	public Map<Path, N4JSProjectName> getCoreProjectPaths() {
		return Collections.unmodifiableMap(coreProjectPaths);
	}

	/**
	 * Adds entries to the {@link #getCoreProjectPaths() core project paths}. All previously stored values are removed,
	 * and all provided values are stored.
	 */
	public void setCoreProjectPaths(Map<Path, N4JSProjectName> paths) {
		this.coreProjectPaths.clear();
		this.coreProjectPaths.putAll(paths);
	}

	/**
	 * Unlike {@link #setCoreProjectPaths(Map)} this method adds new entries without removing previous values.
	 */
	public void addCoreProjectPaths(Map<Path, N4JSProjectName> paths) {
		this.coreProjectPaths.putAll(paths);
	}

	/**
	 * Returns a new map containing all values of the receiving run configuration that are to be persisted. Values in
	 * the returned map may only be of type <code>Boolean</code>, <code>String</code>, or <code>List&lt;String></code>,
	 * or <code>Map&lt;String,String></code>.
	 * <p>
	 * Subclasses may override to add more persistent values, but then the super-class implementation should be invoked
	 * and method {@link #writePersistentValues(Map)} should be customized accordingly.
	 */
	public Map<String, Object> readPersistentValues() {
		final Map<String, Object> result = new HashMap<>();
		result.put(NAME, this.name);
		result.put(RUNNER_ID, this.runnerId);
		result.put(RUNTIME_ENVIRONMENT, toString(this.runtimeEnvironment.getProjectName()));
		result.put(IMPLEMENTATION_ID, toString(this.implementationId));
		result.put(USER_SELECTION, this.userSelection.toString());
		result.put(ENGINE_OPTIONS, getEngineOptions());
		result.put(RUN_OPTIONS, getRunOptions());
		result.put(ENV_VARS, this.getEnvironmentVariables());
		return result;
	}

	private static String toString(N4JSProjectName projectName) {
		if (projectName == null) {
			return null;
		}
		return projectName.getRawName();
	}

	private static N4JSProjectName toName(String projectName) {
		if (projectName == null) {
			return null;
		}
		return new N4JSProjectName(projectName);
	}

	/**
	 * Writes all values from the given map into the receiving run configuration that were returned by method
	 * {@link #readPersistentValues()}.
	 * <p>
	 * Subclasses may override to add more persistent values, but then the super-class implementation should be invoked
	 * and method {@link #readPersistentValues()} should be customized accordingly.
	 */
	public void writePersistentValues(Map<String, Object> map) {
		this.name = getString(map, NAME, false);
		this.runnerId = getString(map, RUNNER_ID, false);
		this.runtimeEnvironment = RuntimeEnvironment
				.fromProjectName(toName(getString(map, RUNTIME_ENVIRONMENT, false)));
		this.implementationId = toName(getString(map, IMPLEMENTATION_ID, true));
		this.userSelection = getURI(map, USER_SELECTION, false);
		this.engineOptions = nullToEmpty(getString(map, ENGINE_OPTIONS, true));
		this.runOptions = nullToEmpty(getString(map, RUN_OPTIONS, true));

		this.setEnvironmentVariables(getMap(map, ENV_VARS, true));
	}

	/**
	 * Fail-fast method for reading a value from a map as returned by method {@link #readPersistentValues()}.
	 */
	public static final boolean getBoolean(Map<String, Object> map, String key) {
		final Object value = map.get(key);
		if (value == null)
			throw new IllegalArgumentException("no value for key '" + key + "'");
		if (!(value instanceof Boolean))
			throw new IllegalArgumentException("value for key '" + key
					+ "' is expected to be of type Boolean but was: " + value.getClass().getName());
		return ((Boolean) value).booleanValue();
	}

	/**
	 * Fail-fast method for reading a value from a map as returned by method {@link #readPersistentValues()}.
	 */
	public static final String getString(Map<String, Object> map, String key, boolean allowNull) {
		final Object value = map.get(key);
		if (value == null && !allowNull)
			throw new IllegalArgumentException("no value for key '" + key + "'");
		if (value != null && !(value instanceof String))
			throw new IllegalArgumentException("value for key '" + key
					+ "' is expected to be of type String but was: " + value.getClass().getName());
		return (String) value;
	}

	/**
	 * Fail-fast method for reading a value from a map as returned by method {@link #readPersistentValues()}.
	 */
	@SuppressWarnings("unchecked")
	public static final Map<String, String> getMap(Map<String, Object> map, String key, boolean allowNull) {
		final Object value = map.get(key);
		if (value == null && !allowNull)
			throw new IllegalArgumentException("no value for key '" + key + "'");
		if (value != null && !(value instanceof Map))
			throw new IllegalArgumentException("value for key '" + key
					+ "' is expected to be of type Map but was: " + value.getClass().getName());
		return (Map<String, String>) value;
	}

	/**
	 * Fail-fast method for reading a value from a map as returned by method {@link #readPersistentValues()}.
	 */
	public static final URI getURI(Map<String, Object> map, String key, boolean allowNull) {
		final String value = getString(map, key, allowNull);
		return value != null ? URI.createURI(value) : null;
	}

	/**
	 * Fail-fast method for reading a value from a map as returned by method {@link #readPersistentValues()}.
	 */
	public static final List<String> getListOfString(Map<String, Object> map, String key, boolean allowNull) {
		final Object value = map.get(key);
		if (value == null) {
			if (!allowNull) {
				throw new IllegalArgumentException("no value for key '" + key + "'");
			}
			return Collections.emptyList();
		}
		// Following usage of raw-types is due to a javac-compiler crash in version 1.8.0_40 when using WildCards:
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final boolean wrongType = !(value instanceof List)
				|| ((List) value).stream().anyMatch(v -> !(v instanceof String));
		if (wrongType)
			throw new IllegalArgumentException("value for key '" + key
					+ "' is expected to be of type List<String> but was: " + value.getClass().getName());
		@SuppressWarnings("unchecked")
		final List<String> result = (List<String>) value;
		return result;
	}

}
