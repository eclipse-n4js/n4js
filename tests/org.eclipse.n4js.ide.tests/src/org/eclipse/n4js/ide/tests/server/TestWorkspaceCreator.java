/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Project.SourceFolder;
import org.eclipse.n4js.tests.codegen.YarnWorkspaceProject;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Streams;

/**
 * Utility to create npm/yarn workspaces for test purposes
 */
public class TestWorkspaceCreator {

	/** Folder where test data is created */
	static final public String TEST_DATA_FOLDER = "/test-workspace";
	/** Vendor of the created test project */
	static final public String VENDOR = "VENDOR";
	/** Name of the created test module */
	static final public String SRC_FOLDER = "src";
	/** Default extension of test modules */
	static final public String DEFAULT_EXTENSION = "n4js";
	/** Default name of the created test project */
	static final public String DEFAULT_PROJECT_NAME = "test-project";
	/** Default name of the created test module */
	static final public String DEFAULT_MODULE_NAME = "MyModule";
	/** Reserved string to identify comma separated list of dependencies to other projects */
	static final public String DEPENDENCIES = "#DEPENDENCY";
	/** Reserved string to identify the directory 'node_modules' */
	static final public String NODE_MODULES = "#NODE_MODULES:";
	/** Reserved string to identify the directory 'node_modules' */
	static final public String PACKAGE_JSON = "package.json";
	/** Reserved string to identify the src folder of a project */
	static final public String SRC = "#SRC:";
	/** Name of n4js library 'n4js-runtime' */
	static final public String N4JS_RUNTIME_NAME = "n4js-runtime";
	/** Default project object for 'n4js-runtime' */
	static final public Project N4JS_RUNTIME_FAKE = new Project(N4JS_RUNTIME_NAME, VENDOR, VENDOR + "_name",
			ProjectType.RUNTIME_ENVIRONMENT);

	/**
	 * Project type used for all projects created.
	 * <p>
	 * Implementation note:<br>
	 * In case you like to refactor this, consider to align this analogously to {@link #DEPENDENCIES}.
	 */
	private final ProjectType projectType;

	TestWorkspaceCreator(ProjectType projectType) {
		this.projectType = projectType;
	}

	/** Data class to hold name and extension of a file */
	public static class NameAndExtension {
		/** File name */
		final String name;
		/** File extension */
		final String extension;

		NameAndExtension(String name, String extension) {
			this.name = name;
			this.extension = extension;
		}
	}

	/**
	 * Returns file name and extension for a given file name. In case the given file name contains a known extension
	 * (c.f. {@link N4JSGlobals#ALL_N4_FILE_EXTENSIONS}), this is recognized. Otherwise, the extension 'n4js' is added
	 * as default.
	 */
	public NameAndExtension getN4JSNameAndExtension(String fileName) {
		String name = fileName;
		String extension = null;
		if (fileName != null && fileName.contains(".")) {
			String[] split = fileName.split("\\.");
			extension = split[split.length - 1];
			if (N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(extension)) {
				name = fileName.substring(0, fileName.length() - extension.length() - 1);
			}
		}
		return new NameAndExtension(name, extension);
	}

	/** @return the workspace root folder as a {@link File}. */
	public File getRoot() {
		File root = new File(new File("").getAbsoluteFile(), TEST_DATA_FOLDER);
		return root;
	}

	/** @return the given name if non-<code>null</code> and non-empty; otherwise {@link #DEFAULT_MODULE_NAME}. */
	public String getModuleNameOrDefault(String moduleName) {
		if (Strings.isNullOrEmpty(moduleName)) {
			return DEFAULT_MODULE_NAME;
		}
		return moduleName;
	}

	/** Translates a given module name to a file URI used in LSP call data. */
	public FileURI getFileURIFromModuleName(String moduleName) {
		String extension = getN4JSNameAndExtension(moduleName).extension == null ? "." + DEFAULT_EXTENSION : "";
		String moduleNameWithExtension = getModuleNameOrDefault(moduleName) + extension;
		try {
			List<Path> allMatches = Files
					.find(getRoot().toPath(), 99, (path, options) -> path.endsWith(moduleNameWithExtension))
					.collect(Collectors.toList());

			if (allMatches.isEmpty()) {
				throw new IllegalStateException("Module not found with name " + moduleNameWithExtension);
			}
			if (allMatches.size() > 1) {
				throw new IllegalStateException("Multiple modules found with name " + moduleNameWithExtension);
			}

			return new FileURI(allMatches.get(0).toFile());

		} catch (IOException e) {
			throw new IllegalStateException("Error when searching for module " + moduleNameWithExtension, e);
		}
	}

	/**
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as one or more
	 * {@link Pair}s.
	 */
	public Project createTestProjectOnDisk(@SuppressWarnings("unchecked") Pair<String, String>... modulesContents) {
		return createTestProjectOnDisk(Arrays.asList(modulesContents));
	}

	/**
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as an iterable
	 * of {@link Pair}s.
	 */
	public Project createTestProjectOnDisk(Iterable<? extends Pair<String, String>> modulesContents) {
		Map<String, String> modulesContentsAsMap = Streams.stream(modulesContents)
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));

		return createTestProjectOnDisk(modulesContentsAsMap);
	}

	/** Creates the default project on file system. Adds dependency to n4js-runtime. */
	public Project createTestProjectOnDisk(Map<String, String> modulesContents) {
		return createClientProject(getRoot().toPath(), DEFAULT_PROJECT_NAME, modulesContents);
	}

	private Project createClientProject(Path destination, String projectName, Map<String, String> modulesContents) {
		Map<String, String> modulesContentsCpy = new HashMap<>(modulesContents);
		LinkedHashMap<String, Map<String, String>> projectsModulesContents = new LinkedHashMap<>();
		projectsModulesContents.put(projectName, modulesContentsCpy);
		modulesContentsCpy.put(NODE_MODULES + N4JS_RUNTIME_NAME, null);
		modulesContentsCpy.put(DEPENDENCIES, N4JS_RUNTIME_NAME);

		return createTestOnDisk(destination, projectsModulesContents);
	}

	/** Creates the default project on file system. */
	public Project createTestOnDisk(LinkedHashMap<String, Map<String, String>> projectsModulesContents) {
		return createTestOnDisk(getRoot().toPath(), projectsModulesContents);
	}

	private Project createTestOnDisk(Path destination,
			LinkedHashMap<String, Map<String, String>> projectsModulesContents) {

		Project project = null;
		if (projectsModulesContents.size() == 1) {
			Entry<String, Map<String, String>> singleProject = projectsModulesContents.entrySet().iterator().next();
			String projectName = singleProject.getKey();
			Map<String, String> moduleContents = singleProject.getValue();
			project = createSimpleProject(projectName, moduleContents, HashMultimap.create());
		} else {
			project = createYarnProject(projectsModulesContents);
		}

		destination.toFile().mkdirs();
		project.create(destination);

		return project;
	}

	private Project createSimpleProject(String projectName, Map<String, String> modulesContents,
			Multimap<String, String> dependencies) {

		if (projectName.equals(N4JS_RUNTIME_NAME) && (modulesContents == null || modulesContents.isEmpty())) {
			return N4JS_RUNTIME_FAKE;
		}

		ProjectType prjType = projectName.equals(N4JS_RUNTIME_NAME)
				? ProjectType.RUNTIME_ENVIRONMENT
				: projectType;

		Project project = new Project(projectName, VENDOR, VENDOR + "_name", prjType);
		SourceFolder sourceFolder = project.createSourceFolder(SRC_FOLDER);

		for (String moduleName : modulesContents.keySet()) {
			String contents = modulesContents.get(moduleName);
			if (moduleName.equals(DEPENDENCIES)) {
				String[] allDeps = contents.split(",");
				for (String dependency : allDeps) {
					dependencies.put(projectName, dependency.trim());
				}

			} else if (moduleName.equals(PACKAGE_JSON)) {
				project.setProjectDescriptionContent(contents);

			} else if (moduleName.startsWith(NODE_MODULES)) {
				int indexOfSrc = moduleName.indexOf(SRC);
				if (moduleName.equals(NODE_MODULES + N4JS_RUNTIME_NAME) && indexOfSrc == -1) {
					project.addNodeModuleProject(N4JS_RUNTIME_FAKE);

				} else {
					if (indexOfSrc == -1) {
						throw new IllegalArgumentException("Missing #SRC: in module location");
					}
					String nmModuleName = moduleName.substring(indexOfSrc + SRC.length());
					String nmName = moduleName.substring(NODE_MODULES.length(), indexOfSrc);
					Project nmProject = project.getNodeModuleProject(nmName);
					if (nmProject == null) {
						nmProject = new Project(nmName, VENDOR, VENDOR + "_name", prjType);
						nmProject.createSourceFolder(SRC_FOLDER);
						project.addNodeModuleProject(nmProject);
					}
					SourceFolder nmSourceFolder = nmProject.getSourceFolders().get(0);
					createAndAddModule(contents, nmModuleName, nmSourceFolder);
				}

			} else {
				createAndAddModule(contents, moduleName, sourceFolder);
			}
		}

		return project;
	}

	private void createAndAddModule(String contents, String moduleName, SourceFolder nmSourceFolder) {
		NameAndExtension nae = getN4JSNameAndExtension(moduleName);
		Module module = nae.extension == null ? new Module(moduleName) : new Module(nae.name, nae.extension);
		module.setContents(contents);
		nmSourceFolder.addModule(module);
	}

	private Project createYarnProject(LinkedHashMap<String, Map<String, String>> projectsModulesContents) {
		YarnWorkspaceProject yarnProject = new YarnWorkspaceProject("yarn-test-project", VENDOR, VENDOR + "_name");
		Multimap<String, String> dependencies = HashMultimap.create();
		for (String projectNameWithSelector : projectsModulesContents.keySet()) {
			Map<String, String> moduleContents = projectsModulesContents.get(projectNameWithSelector);

			String prjName = projectNameWithSelector;

			if (prjName.startsWith(NODE_MODULES)) {
				prjName = prjName.substring(NODE_MODULES.length());
				Project project = createSimpleProject(prjName, moduleContents, dependencies);
				yarnProject.addNodeModuleProject(project);
			} else {
				Project project = createSimpleProject(prjName, moduleContents, dependencies);
				yarnProject.addProject(project);
			}
		}

		setDependencies(yarnProject, dependencies);

		return yarnProject;
	}

	private void setDependencies(YarnWorkspaceProject yarnProject, Multimap<String, String> dependencies) {
		for (String projectName : dependencies.keySet()) {
			Collection<String> projectDependencies = dependencies.get(projectName);
			Project project = yarnProject.getProject(projectName);
			for (String projectDependency : projectDependencies) {
				Project dependency = yarnProject.getProject(projectDependency);
				if (dependency == null) {
					dependency = yarnProject.getNodeModuleProject(projectDependency);
				}
				project.addProjectDependency(dependency);
			}
		}
	}
}
