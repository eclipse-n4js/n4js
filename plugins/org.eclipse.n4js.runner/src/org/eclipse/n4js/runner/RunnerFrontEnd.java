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

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.fileextensions.FileExtensionType;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.generator.AbstractSubGenerator;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RunnerHelper.ApiUsage;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerRegistry;
import org.eclipse.n4js.utils.ResourceNameComputer;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Entry point for running N4JS code. All client code should only use this class and the corresponding class
 * <code>RunnerFrontEndUI</code> for running code within Eclipse.
 */
@Singleton
public class RunnerFrontEnd {

	@Inject
	private IN4JSCore in4jscore;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private RunnerHelper runnerHelper;

	@Inject
	private RunnerRegistry runnerRegistry;

	@Inject
	private FileExtensionsRegistry fileExtensionsRegistry;

	/**
	 * Returns true iff the runner with the given id can run the given moduleToRun. Takes same arguments as
	 * {@link #createConfiguration(String, N4JSProjectName, URI)}.
	 */
	@SuppressWarnings("unused")
	public boolean canRun(String runnerId, URI moduleToRun) {
		// FIXME IDE-1393 implement this method and call it from SupportingRunnerPropertyTester
		throw new UnsupportedOperationException();
	}

	/**
	 * Create a new run configuration from scratch for running the given moduleToRun.
	 *
	 * @param runnerId
	 *            identifier of the runner to use.
	 * @param moduleToRun
	 *            the module to execute. When running in Eclipse, this will be a platform resource URI, in the headless
	 *            case it will be a file URI.
	 * @return the run configuration.
	 */
	public RunConfiguration createConfiguration(String runnerId, URI moduleToRun) {
		return createConfiguration(runnerId, null, moduleToRun);
	}

	/**
	 * Create a new run configuration from scratch for running the given moduleToRun.
	 *
	 * @param runnerId
	 *            identifier of the runner to use.
	 * @param implementationId
	 *            implementation ID to use or <code>null</code>. See {@link RunConfiguration#getImplementationId() here}
	 *            for details.
	 * @param moduleToRun
	 *            the module to execute. When running in Eclipse, this will be a platform resource URI, in the headless
	 *            case it will be a file URI.
	 * @return the run configuration.
	 */
	public RunConfiguration createConfiguration(String runnerId, N4JSProjectName implementationId, URI moduleToRun) {
		final IRunnerDescriptor runnerDesc = runnerRegistry.getDescriptor(runnerId);
		final IRunner runner = runnerDesc.getRunner();

		final RunConfiguration config = runner.createConfiguration();
		config.setName(runnerHelper.computeConfigurationName(runnerId, moduleToRun));
		config.setRunnerId(runnerId);
		config.setRuntimeEnvironment(runnerDesc.getEnvironment());
		config.setImplementationId(implementationId);
		config.setUserSelection(moduleToRun);

		computeDerivedValues(config);

		return config;
	}

	/**
	 * Allows for adding additional path if needed.
	 */
	public RunConfiguration createConfiguration(String runnerId, N4JSProjectName implementationId, URI moduleToRun,
			String additionalPath) {

		RunConfiguration runConfig = createConfiguration(runnerId, implementationId, moduleToRun);
		runConfig.addAdditionalPath(additionalPath);
		return runConfig;
	}

	/**
	 * Restores a run configuration that was previously "serialized" with method
	 * {@link RunConfiguration#readPersistentValues()}. This method should only be used by
	 * <code>RunConfigurationConverter</code>.
	 */
	public RunConfiguration createConfiguration(Map<String, Object> values) {
		final String runnerId = RunConfiguration.getString(values, RunConfiguration.RUNNER_ID, false);
		final IRunner runner = runnerRegistry.getRunner(runnerId);

		final RunConfiguration config = runner.createConfiguration();
		config.writePersistentValues(values);

		computeDerivedValues(config);

		return config;
	}

	/**
	 * Create runner-config customized for this Xpect test. cf. org.eclipse.n4js.xpect.XpectN4JSES5TranspilerHelper
	 */
	public RunConfiguration createXpectOutputTestConfiguration(String runnerId,
			String userSelectionNodePathResolvableTargetFileName, Path additionalProjectPath,
			N4JSProjectName additionalProjectName) {

		final IRunnerDescriptor runnerDesc = runnerRegistry.getDescriptor(runnerId);
		final IRunner runner = runnerDesc.getRunner();

		final RunConfiguration config = runner.createConfiguration();
		config.setName(runnerId + "__" + userSelectionNodePathResolvableTargetFileName);
		config.setRuntimeEnvironment(runnerDesc.getEnvironment());
		config.setImplementationId(null);
		config.setRunnerId(runnerId);

		config.setCoreProjectPaths(ImmutableMap.of(additionalProjectPath, additionalProjectName));

		config.setWorkingDirectory(additionalProjectPath);
		config.setFileToRun(Paths.get(userSelectionNodePathResolvableTargetFileName));

		config.setExecutionData(RunConfiguration.EXEC_DATA_KEY__USER_SELECTION,
				userSelectionNodePathResolvableTargetFileName);

		IRunner preparedRunner = runnerRegistry.getRunner(runnerId);
		preparedRunner.prepareConfiguration(config);

		return config;
	}

	/**
	 * Computes all derived values in the given run configuration from the primary values (see {@link RunConfiguration
	 * here} for details on primary and derived values). Additionally delegated to dynamically obtained runner to
	 * customize derived values further.
	 * <p>
	 * This method is called from methods {@link #createConfiguration(String, N4JSProjectName, URI)} and
	 * {@link #createConfiguration(Map)}, so client code usually does not need to call it directly.
	 */
	public void computeDerivedValues(RunConfiguration config) {
		computeDerivedValues(config, true);
	}

	/**
	 * Same as {@link #computeDerivedValues(RunConfiguration)} but does not configure settings for the concrete runner.
	 */
	public void computeDerivedValues(RunConfiguration config, boolean delegateToRunnerCustomization) {

		configureWorkingDirectory(config);

		configureFileToRun(config);

		configureDependenciesAndPaths(config);

		configureExecutionData(config);

		if (delegateToRunnerCustomization) {
			// delegate further computation to the specific runner implementation
			IRunner runner = runnerRegistry.getRunner(config);
			runner.prepareConfiguration(config);
		}
	}

	/**
	 * Configures the root folder of the user selection's containing N4JS project as working directory.
	 */
	private void configureWorkingDirectory(RunConfiguration config) {
		final URI userSelection = config.getUserSelection();
		if (userSelection != null) {
			IN4JSProject containingProject = resolveProject(userSelection);
			Path workingDirectory = containingProject.getLocation().toFileSystemPath();
			config.setWorkingDirectory(workingDirectory);
		}
	}

	/**
	 * Configures the file the user selection is pointing to as the file to run. If the user selection is not pointing
	 * to a file (e.g. a folder) the file to run will be reset to <code>null</code>.
	 */
	private void configureFileToRun(RunConfiguration config) {
		final URI userSelection = config.getUserSelection();
		if (userSelection != null && hasValidFileExtension(userSelection.toString())) {
			final String selectedFileDescriptor = resourceNameComputer.generateFileDescriptor(userSelection, null);
			final Path selectedFilePath = new File(selectedFileDescriptor).toPath();
			final IN4JSProject containingProject = resolveProject(userSelection);
			final String outputPathStr = containingProject.getOutputPath();
			final Path outputPath = Paths.get(outputPathStr.replace('/', File.separatorChar));
			final Path fileToRun = outputPath.resolve(selectedFilePath);
			config.setFileToRun(fileToRun);
		} else {
			// this can happen if the RunConfiguration 'config' is actually a TestConfiguration, because then the user
			// selection is allowed to point to a project or folder (and method CompilerUtils#getModuleName() above
			// would throw an exception)
			config.setFileToRun(null);
		}
	}

	/**
	 * Configures paths based on project dependencies and API-IMPL relations.
	 */
	private void configureDependenciesAndPaths(RunConfiguration config) {
		// 1) for all API projects among the direct and indirect dependencies we have to provide a mapping
		// from the projectName of the API project to the projectName of the implementation project to be used
		final ApiUsage apiUsage = runnerHelper.getProjectExtendedDepsAndApiImplMapping(
				config.getRuntimeEnvironment(),
				config.getUserSelection(),
				config.getImplementationId() != null ? config.getImplementationId() : null,
				true);

		final List<IN4JSProject> deps = apiUsage.projects;
		final Map<IN4JSProject, IN4JSProject> apiImplProjectMapping = apiUsage.concreteApiImplProjectMapping;

		config.setApiImplProjectMappingFromProjects(apiImplProjectMapping);

		// 2) collect paths to all output folders of all N4JS projects (dependencies) with the compiled code
		// (these are the .../es5/ folders)
		final Set<IN4JSProject> depsImpl = deps.stream().map(p -> {
			final IN4JSProject p2 = apiImplProjectMapping.get(p);
			final IN4JSProject p3 = p2 != null ? p2 : p;
			return p3;
		}).collect(Collectors.toCollection(() -> Sets.newLinkedHashSet()));
		final Map<Path, N4JSProjectName> coreProjectPaths = runnerHelper.getCoreProjectPaths(depsImpl);
		config.setCoreProjectPaths(coreProjectPaths);
	}

	/**
	 * Configures execution data from user selection, init modules, and apiImplProjectMapping
	 *
	 * this should transform user selection into execution data, but concrete runner/tester may transform this further
	 * into its own representation.
	 *
	 * Testers need to pass test discovery result. Runners need to pass module/class/method selection.
	 */
	private void configureExecutionData(RunConfiguration config) {
		final URI userSelection = config.getUserSelection();
		if (userSelection != null && (hasValidFileExtension(userSelection.toString()))) {
			final String userSelection_targetFileName = resourceNameComputer.generateFileDescriptor(userSelection,
					null);
			IN4JSProject project = resolveProject(userSelection);
			String base = AbstractSubGenerator.calculateProjectBasedOutputDirectory(project, true);
			config.setExecutionData(RunConfiguration.EXEC_DATA_KEY__USER_SELECTION,
					base + "/" + userSelection_targetFileName);
		} else {
			// this can happen if the RunConfiguration 'config' is actually a TestConfiguration, because then the user
			// selection is allowed to point to a project or folder (and method CompilerUtils#getModuleName() above
			// would throw an exception)
			config.setExecutionData(RunConfiguration.EXEC_DATA_KEY__USER_SELECTION, null);
		}
		config.setExecutionData(RunConfiguration.EXEC_DATA_KEY__PROJECT_NAME_MAPPING,
				config.getApiImplProjectMapping());
	}

	/**
	 * Resolves project from provided URI.
	 */
	private IN4JSProject resolveProject(URI n4jsSourceURI) {
		final Optional<? extends IN4JSProject> optionalProject = in4jscore.findProject(n4jsSourceURI);
		if (!optionalProject.isPresent()) {
			throw new RuntimeException(
					"Cannot handle resource without containing project. Resource URI was: " + n4jsSourceURI + ".");
		}
		return optionalProject.get();
	}

	private boolean hasValidFileExtension(String fileName) {
		for (String fileExtension : fileExtensionsRegistry
				.getFileExtensions(FileExtensionType.RUNNABLE_FILE_EXTENSION)) {
			if (fileName.endsWith("." + fileExtension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Convenience method. Creates a run configuration with {@link #createConfiguration(String, N4JSProjectName, URI)}
	 * and immediately passes it to {@link #run(RunConfiguration)} in order to launch the moduleToRun.
	 */
	public Process run(String runnerId, N4JSProjectName implementationId, URI moduleToRun) throws ExecutionException {
		return run(createConfiguration(runnerId, implementationId, moduleToRun));
	}

	/**
	 * Convenience method. Same as {@link #run(RunConfiguration, IExecutor)}, but uses the default executor returned my
	 * method {@link #createDefaultExecutor()} that will delegate to {@link Runtime#exec(String[], String[], File)}.
	 */
	public Process run(RunConfiguration config) throws ExecutionException {
		return run(config, createDefaultExecutor());
	}

	/**
	 * Launches the given run configuration. The specific runner to use is defined by the runnerId in the given run
	 * configuration and the given executor will be used for launching any external tools.
	 */
	public Process run(RunConfiguration config, IExecutor executor) throws ExecutionException {
		return runnerRegistry.getRunner(config).run(config, executor);
	}

	/**
	 * Returns a new default executor that will delegate execution to {@link Runtime#exec(String[], String[], File)}.
	 * This executor is mainly intended for headless case, but may be useful in the UI case as well. However, the main
	 * executor to be used in the UI case is returned by method <code>RunnerFrontEndUI#createUIExecutor()</code>.
	 */
	public IExecutor createDefaultExecutor() {
		return new IExecutor() {
			@Override
			public Process exec(String[] cmdLine, File workingDirectory, Map<String, String> envp)
					throws ExecutionException {

				ProcessBuilder pb = new ProcessBuilder(cmdLine);
				pb.environment().putAll(envp);
				pb.directory(workingDirectory);
				pb.inheritIO();

				// Special case for running from Java-code & capturing output of run-result:
				String errFile = System.getProperty("org.eclipse.n4js.runner.RunnerFrontEnd.ERRORFILE", "");
				String outFile = System.getProperty("org.eclipse.n4js.runner.RunnerFrontEnd.OUTPUTFILE", "");
				if (errFile != null && errFile.length() > 0) {
					pb.redirectError(Redirect.to(new File(errFile)));
				}
				if (outFile != null && outFile.length() > 0) {
					pb.redirectOutput(Redirect.to(new File(outFile)));
				}

				try {
					return pb.start();
				} catch (IOException e) {
					throw new ExecutionException(e);
				}
			}
		};
	}
}
