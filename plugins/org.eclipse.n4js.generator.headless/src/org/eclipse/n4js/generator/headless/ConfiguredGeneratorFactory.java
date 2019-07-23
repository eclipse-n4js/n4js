/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator.headless;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.inject.Inject;

/**
 * Factory for {@link ConfiguredGenerator} that created isntances configured and ready to be used for a specific
 * {@link IN4JSProject}.
 *
 * In Eclipse-compile mode there are "projects" and the FSA is configured relative to these projects. In headless mode
 * using {@link org.eclipse.n4js.internal.FileBasedWorkspace} there is no "project"-concept for the generator. So the
 * paths of the FSA need to be reconfigured to contain the navigation to the IN4JSProject-root. We are setting the
 * compile output-configurations to contain path-locations relative to the {@code user.dir}.
 *
 * Wrapper function written against Xtext 2.7.1.
 */
public class ConfiguredGeneratorFactory {

	/** The composite generator that manages all subgenerators. */
	@Inject
	private ICompositeGenerator compositeGenerator;

	/** Abstraction to the file system, used by the generators */
	@Inject
	private JavaIoFileSystemAccess fsa;

	/** Lazily computed configurations based on the {@link #compositeGenerator} */
	private Map<String, OutputConfiguration> initialOutputConfiguration;

	ConfiguredGenerator getConfiguredGenerator(IN4JSProject project) {
		configureFSA(project);
		return new ConfiguredGenerator(compositeGenerator, fsa);
	}

	/**
	 * Setting the compile output-configurations to contain path-locations relative to the user.dir: Wrapper function
	 * written against Xtext 2.7.1.
	 *
	 * In Eclipse-compile mode there are "projects" and the FSA is configured relative to these projects. In this
	 * filebasedWorkspace here there is no "project"-concept for the generator. So the paths of the FSA need to be
	 * reconfigured to contain the navigation to the IN4JSProject-root.
	 *
	 * @param project
	 *            project to be compiled
	 */
	private void configureFSA(IN4JSProject project) {
		File currentDirectory = new File(".");
		File projectLocation = project.getLocation().toJavaIoFile();

		// If project is not in a sub directory of the current directory an absolute path is computed.
		final java.net.URI projectURI = currentDirectory.toURI().relativize(projectLocation.toURI());
		final String projectPath = projectURI.getPath();
		if (projectPath.length() != 0) {// not the same directory, reconfigure

			if (initialOutputConfiguration == null)
				initialOutputConfiguration = getInitialOutputConfigurations(compositeGenerator);
			Map<String, OutputConfiguration> configs = transformedOutputConfiguration(projectPath,
					initialOutputConfiguration);
			fsa.setOutputConfigurations(configs);
		}
	}

	/**
	 * Wraps the output-configurations with a delegate that transparently injects the relative path to the project-root.
	 *
	 * @param projectPath
	 *            relative path to the project-root
	 * @return wrapped configurations.
	 */
	private static Map<String, OutputConfiguration> transformedOutputConfiguration(String projectPath,
			Map<String, OutputConfiguration> outputConfigToBeWrapped) {
		Map<String, OutputConfiguration> result = new HashMap<>();

		outputConfigToBeWrapped.forEach((name, config) -> {
			result.put(name, new WrappedOutputConfiguration(config, projectPath));
		});

		return result;
	}

	/** Build an output configuration from a composite generator. */
	private static Map<String, OutputConfiguration> getInitialOutputConfigurations(ICompositeGenerator generator) {
		return generator
				.getCompilerDescriptors().stream().collect(Collectors.toMap(
						CompilerDescriptor::getIdentifier,
						CompilerDescriptor::getOutputConfiguration));
	}

}
