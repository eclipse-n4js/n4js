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
package org.eclipse.n4js.ide.tests.helper.server;

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
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Streams;

/**
 * Utility to create npm/yarn workspaces for test purposes
 * <p>
 * Note the {@link Documentation}.
 */
public class TestWorkspaceManager {

	/** Special suffix to denote a "selected" module (to be appended to the module name). */
	static final public String MODULE_SELECTOR = "*";

	/** Folder where test data is created */
	static final public String TEST_DATA_FOLDER = "/test-workspace";
	/** Vendor of the created test project */
	static final public String VENDOR = "VENDOR";
	/** Default name of the source folder */
	static final public String DEFAULT_SOURCE_FOLDER = "src";
	/** Default extension of test modules */
	static final public String DEFAULT_EXTENSION = "n4js";
	/** Default name of the created test project */
	static final public String DEFAULT_PROJECT_NAME = "test-project";
	/** Name of the yarn workspace project created iff a test uses more than one test project. */
	static final public String YARN_TEST_PROJECT = "yarn-test-project";
	/** Default name of the created test module */
	static final public String DEFAULT_MODULE_NAME = "MyModule";
	/**
	 * Reserved string to define the project's dependencies to other projects; the value must be a comma separated list
	 * of project names.<br>
	 * see {@link Documentation#CFG_DEPENDENCIES}
	 */
	static final public String CFG_DEPENDENCIES = "#DEPENDENCY";
	/**
	 * Reserved string to define the main module property "main" in the package.json.<br>
	 * see {@link Documentation#CFG_MAIN_MODULE}
	 */
	static final public String CFG_MAIN_MODULE = "#MAIN_MODULE";
	/**
	 * Reserved string to defined the source folder in the package.json.<br>
	 * Usage is similar to {@link #CFG_MAIN_MODULE}, see {@link Documentation#CFG_MAIN_MODULE}.
	 */
	static final public String CFG_SOURCE_FOLDER = "#SOURCE_FOLDER";
	/**
	 * Reserved string to identify the directory 'node_modules'<br>
	 * see {@link Documentation#PROJECT_NODE_MODULES} and {@link Documentation#WORKSPACE_NODE_MODULES}
	 */
	static final public String CFG_NODE_MODULES = "#NODE_MODULES:";
	/**
	 * Reserved string to identify the name of the workspaces folder name<br>
	 * see {@link Documentation#CFG_WORKSPACES_FOLDER}
	 */
	static final public String CFG_WORKSPACES_FOLDER = "#CFG_WORKSPACES_FOLDER:";
	/**
	 * Reserved string to identify the src folder of a project<br>
	 * see {@link #CFG_NODE_MODULES}
	 */
	static final public String CFG_SRC = "#SRC:";
	/** Name of n4js library 'n4js-runtime' */
	static final public String N4JS_RUNTIME = N4JSGlobals.N4JS_RUNTIME.getRawName();
	/** Default project object for 'n4js-runtime' */
	static final public Project N4JS_RUNTIME_FAKE = new Project(N4JS_RUNTIME, VENDOR, VENDOR + "_name",
			ProjectType.RUNTIME_ENVIRONMENT);

	/**
	 * Project type used for all projects created.
	 * <p>
	 * Implementation note:<br>
	 * In case you like to refactor this, consider to align this analogously to {@link #CFG_DEPENDENCIES}.
	 */
	private final ProjectType projectType;

	/** @see #getCreatedProject() */
	private Project createdProject;

	/** Constructor */
	public TestWorkspaceManager(ProjectType projectType) {
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

	/** Same as {@link #getProjectRoot(String)}, but for the {@link #DEFAULT_PROJECT_NAME default project}. */
	public File getProjectRoot() {
		return getProjectRoot(DEFAULT_PROJECT_NAME);
	}

	/** Returns the root folder of the project with the given name. */
	public File getProjectRoot(String projectName) {
		assertCreated();
		File projectFolder = getProjectRootFailSafe(projectName);
		// for consistency with #getFileURIFromModuleName() we require the folder to exist:
		if (projectFolder == null) {
			throw new IllegalStateException("cannot find project for project name: " + projectName);
		}
		if (!projectFolder.isDirectory()) {
			throw new IllegalStateException(
					"project folder of project \"" + projectName + "\" does not exist: " + projectFolder);
		}
		return projectFolder;
	}

	private File getProjectRootFailSafe(String projectName) {
		if (!isCreated()) {
			return null;
		}

		Path projectNamePath = projectNameToRelativePath(projectName);
		Path createdProjectNamePath = projectNameToRelativePath(createdProject.getName());

		Path createdProjectPath = getRoot().toPath().resolve(createdProjectNamePath);

		if (projectName.equals(createdProject.getName())) {
			return createdProjectPath.toFile();
		}

		if (createdProject instanceof YarnWorkspaceProject) {
			Project containedProject = ((YarnWorkspaceProject) createdProject).getMemberProject(projectName);
			if (containedProject != null) {
				return createdProjectPath.resolve(YarnWorkspaceProject.PACKAGES).resolve(projectNamePath).toFile();
			}
		}

		Project nodeModuleProject = createdProject.getNodeModuleProject(projectName);
		if (nodeModuleProject != null) {
			return createdProjectPath.resolve(N4JSGlobals.NODE_MODULES).resolve(projectNamePath).toFile();
		}

		if (createdProject instanceof YarnWorkspaceProject) {
			for (Project containedProject : ((YarnWorkspaceProject) createdProject).getMemberProjects()) {
				Project containedNodeModuleProject = createdProject.getNodeModuleProject(projectName);
				if (containedNodeModuleProject != null) {
					Path containedProjectNamePath = projectNameToRelativePath(containedProject.getName());
					return createdProjectPath
							.resolve(YarnWorkspaceProject.PACKAGES).resolve(containedProjectNamePath)
							.resolve(N4JSGlobals.NODE_MODULES).resolve(projectNamePath).toFile();
				}
			}
		}

		return null;
	}

	private Path projectNameToRelativePath(String projectName) {
		String namePathStr = "/".equals(File.separator) ? projectName : projectName.replace("/", File.separator);
		return Path.of(namePathStr);
	}

	/** Returns the package.json file of the {@link #DEFAULT_PROJECT_NAME default project}. */
	protected File getPackageJsonFile() {
		return getPackageJsonFile(DEFAULT_PROJECT_NAME);
	}

	/** Returns the package.json file of the project with the given name. */
	protected File getPackageJsonFile(String projectName) {
		return new File(getProjectRoot(projectName), N4JSGlobals.PACKAGE_JSON);
	}

	/** @return the given name if non-<code>null</code> and non-empty; otherwise {@link #DEFAULT_MODULE_NAME}. */
	public String getModuleNameOrDefault(String moduleName) {
		if (Strings.isNullOrEmpty(moduleName)) {
			return DEFAULT_MODULE_NAME;
		}
		return moduleName;
	}

	/**
	 * Translates a given module name to a file URI used in LSP call data. When 'moduleName' is <code>null</code>, the
	 * file URI of the {@link #DEFAULT_MODULE_NAME default module} will be returned.
	 * <p>
	 * Because <code>package.json</code> files often play a similar role as modules (e.g. when asserting issues), this
	 * method also supports <code>package.json</code> files, even though they aren't modules: for special names of the
	 * format
	 *
	 * <pre>
	 * &lt;project-name>/package.json
	 * </pre>
	 *
	 * this method will return the file URI of the <code>package.json</code> file of the project with the given name.
	 */
	public FileURI getFileURIFromModuleName(String moduleName) {
		// special case for package.json files:
		if (moduleName != null && moduleName.endsWith("/" + N4JSGlobals.PACKAGE_JSON)) {
			String projectName = moduleName.substring(0, moduleName.length() - (1 + N4JSGlobals.PACKAGE_JSON.length()));
			File packageJsonFile = getPackageJsonFile(projectName);
			return new FileURI(packageJsonFile);
		} else if (moduleName != null && moduleName.contains(N4JSGlobals.PACKAGE_JSON)) {
			// in this case, people probably messed up the special syntax for denoting package.json files:
			Assert.fail("format for denoting package.json inside a module name argument is \"<project name>/"
					+ N4JSGlobals.PACKAGE_JSON + "\", but argument was: \"" + moduleName + "\"");
		}
		// standard case for modules ('moduleName' seems to denote the name of a module):
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

	/** Tells whether the test workspace has already been created. */
	public boolean isCreated() {
		return createdProject != null;
	}

	/** Throws an exception iff the test workspace has not yet been created. */
	public void assertCreated() {
		Assert.assertTrue("no test project(s) created yet", isCreated());
	}

	/**
	 * Returns the project created with one of the <code>create*</code>-methods or <code>null</code> if none was created
	 * yet. In case of yarn workspaces, this method returns the root project and it will be of type
	 * {@link YarnWorkspaceProject}.
	 */
	public Project getCreatedProject() {
		return createdProject;
	}

	/**
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as one or more
	 * {@link Pair}s.
	 */
	@SafeVarargs
	public final Project createTestProjectOnDisk(Pair<String, String>... modulesContents) {
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
		return createTestOnDisk(getRoot().toPath(), ImmutableMap.of(DEFAULT_PROJECT_NAME, modulesContents));
	}

	/** Same as {@link #createTestOnDisk(Map)}, but accepts pairs instead of a map. */
	@SafeVarargs
	public final Project createTestOnDisk(Pair<String, List<Pair<String, String>>>... projectsModulesContents) {
		Map<String, Map<String, String>> projectsModulesContentsAsMap = new LinkedHashMap<>();
		convertProjectsModulesContentsToMap(Arrays.asList(projectsModulesContents), projectsModulesContentsAsMap,
				false);
		return createTestOnDisk(projectsModulesContentsAsMap);
	}

	/**
	 * Creates the given projects and modules on the file system. If several projects are specified, a containing yarn
	 * workspace will be created.
	 *
	 * @return the single project created or, if several projects are created, the containing yarn workspace project.
	 */
	public Project createTestOnDisk(Map<String, Map<String, String>> projectsModulesContents) {
		return createTestOnDisk(getRoot().toPath(), projectsModulesContents);
	}

	private Project createTestOnDisk(Path destination, Map<String, Map<String, String>> projectsModulesContents) {

		if (createdProject != null) {
			throw new IllegalStateException("test was already created on disk");
		}

		if (projectsModulesContents.size() == 1) {
			Entry<String, Map<String, String>> singleProject = projectsModulesContents.entrySet().iterator().next();
			String projectName = singleProject.getKey();
			Map<String, String> modulesContents = singleProject.getValue();
			createdProject = createSimpleProject(projectName, modulesContents, HashMultimap.create(),
					ProjectKind.TopLevel);
		} else {
			createdProject = createYarnProject(projectsModulesContents);
		}

		destination.toFile().mkdirs();
		createdProject.create(destination);

		return createdProject;
	}

	private enum ProjectKind {
		/** A top-level project. */
		TopLevel,
		/** A member project of a yarn workspace. */
		Member,
		/** A project inside a <code>node_modules</code> folder. */
		NodeModule
	}

	private Project createSimpleProject(String projectName, Map<String, String> modulesContents,
			Multimap<String, String> dependencies, ProjectKind projectKind) {

		if (projectName.equals(N4JS_RUNTIME) && (modulesContents == null || modulesContents.isEmpty())) {
			return N4JS_RUNTIME_FAKE;
		}

		ProjectType prjType = projectName.equals(N4JS_RUNTIME)
				? ProjectType.RUNTIME_ENVIRONMENT
				: projectType;

		Project project = new Project(projectName, VENDOR, VENDOR + "_name", prjType);
		String customSourceFolderName = modulesContents.get(CFG_SOURCE_FOLDER);
		SourceFolder sourceFolder = project
				.createSourceFolder(customSourceFolderName != null ? customSourceFolderName : DEFAULT_SOURCE_FOLDER);

		for (String moduleName : modulesContents.keySet()) {
			String contents = modulesContents.get(moduleName);
			if (moduleName.equals(CFG_DEPENDENCIES)) {
				String[] allDeps = contents.split(",");
				for (String dependency : allDeps) {
					String dependencyTrimmed = dependency.trim();
					dependencies.put(projectName, dependencyTrimmed);
					project.addProjectDependency(dependencyTrimmed);
				}

			} else if (moduleName.equals(CFG_MAIN_MODULE)) {
				project.setMainModule(contents);

			} else if (moduleName.equals(CFG_SOURCE_FOLDER)) {
				// ignore (already processed above)

			} else if (moduleName.equals(N4JSGlobals.PACKAGE_JSON)) {
				project.setProjectDescriptionContent(contents);

			} else if (moduleName.startsWith(CFG_NODE_MODULES)) {
				int indexOfSrc = moduleName.indexOf(CFG_SRC);
				if (moduleName.equals(CFG_NODE_MODULES + N4JS_RUNTIME) && indexOfSrc == -1) {
					project.addNodeModuleProject(N4JS_RUNTIME_FAKE);
					project.addProjectDependency(N4JS_RUNTIME_FAKE.getName());

				} else {
					if (indexOfSrc == -1) {
						throw new IllegalArgumentException("Missing #SRC: in module location");
					}
					String nmModuleName = moduleName.substring(indexOfSrc + CFG_SRC.length());
					String nmName = moduleName.substring(CFG_NODE_MODULES.length(), indexOfSrc);
					Project nmProject = project.getNodeModuleProject(nmName);
					if (nmProject == null) {
						nmProject = new Project(nmName, VENDOR, VENDOR + "_name", prjType);
						nmProject.createSourceFolder(DEFAULT_SOURCE_FOLDER);
						project.addNodeModuleProject(nmProject);
						project.addProjectDependency(nmProject.getName());
					}
					SourceFolder nmSourceFolder = nmProject.getSourceFolders().get(0);
					createAndAddModule(contents, nmModuleName, nmSourceFolder);
				}

			} else {
				if (moduleName.startsWith("#")) {
					throw new IllegalArgumentException("unknown reserved string: " + moduleName);
				}

				createAndAddModule(contents, moduleName, sourceFolder);
			}
		}

		// apply default values
		if (N4JSGlobals.PROJECT_TYPES_REQUIRING_N4JS_RUNTIME.contains(project.getType())) {
			// add dependency to n4js-runtime (if not already present)
			project.addProjectDependency(N4JS_RUNTIME);
			// add fake n4js-runtime to node_modules folder (if not already present)
			if (projectKind == ProjectKind.TopLevel) {
				if (project.getNodeModuleProject(N4JS_RUNTIME) == null) {
					project.addNodeModuleProject(N4JS_RUNTIME_FAKE);
				}
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

	private Project createYarnProject(Map<String, Map<String, String>> projectsModulesContents) {
		YarnWorkspaceProject yarnProject = new YarnWorkspaceProject(YARN_TEST_PROJECT, VENDOR, VENDOR + "_name");
		Multimap<String, String> dependencies = HashMultimap.create();
		for (String projectNameWithSelector : projectsModulesContents.keySet()) {
			Map<String, String> moduleContents = projectsModulesContents.get(projectNameWithSelector);

			String prjName = projectNameWithSelector;

			if (prjName.startsWith(CFG_NODE_MODULES)) {
				prjName = prjName.substring(CFG_NODE_MODULES.length());
				Project project = createSimpleProject(prjName, moduleContents, dependencies, ProjectKind.NodeModule);
				yarnProject.addNodeModuleProject(project);

			} else if (prjName.startsWith(CFG_WORKSPACES_FOLDER)) {
				int idxCFG = CFG_WORKSPACES_FOLDER.length();
				int idxSlash = prjName.indexOf("/");
				String wsFolder = prjName.substring(idxCFG, idxSlash);
				prjName = prjName.substring(idxSlash + 1);
				Project project = createSimpleProject(prjName, moduleContents, dependencies, ProjectKind.Member);
				yarnProject.addMemberProject(wsFolder, project);

			} else {
				Project project = createSimpleProject(prjName, moduleContents, dependencies, ProjectKind.Member);
				yarnProject.addMemberProject(project);
			}
		}

		setDependencies(yarnProject, dependencies);

		// apply default values
		boolean haveMemberRequiringN4jsRuntime = IterableExtensions.exists(yarnProject.getMemberProjects(),
				p -> N4JSGlobals.PROJECT_TYPES_REQUIRING_N4JS_RUNTIME.contains(p.getType()));
		if (haveMemberRequiringN4jsRuntime) {
			if (yarnProject.getNodeModuleProject(N4JS_RUNTIME) == null) {
				yarnProject.addNodeModuleProject(N4JS_RUNTIME_FAKE);
			}
		}

		return yarnProject;
	}

	private void setDependencies(YarnWorkspaceProject yarnProject, Multimap<String, String> dependencies) {
		for (String projectName : dependencies.keySet()) {
			Collection<String> projectDependencies = dependencies.get(projectName);
			Project project = yarnProject.getMemberProject(projectName);
			if (project == null) {
				project = yarnProject.getNodeModuleProject(projectName);
				if (project == null) {
					throw new IllegalStateException("unknown project: " + projectName);
				}
			}
			for (String projectDependency : projectDependencies) {
				Project dependency = yarnProject.getMemberProject(projectDependency);
				if (dependency == null) {
					dependency = yarnProject.getNodeModuleProject(projectDependency);
					if (dependency == null) {
						// unresolved dependency in a package.json file
						// -> this might be a valid test case, so ignore this problem
						continue;
					}
				}
				project.addProjectDependency(dependency.getName());
			}
		}
	}

	/** Deletes the test workspace. Throws exception if it has not yet been created. */
	public void deleteTestFromDisk() throws IOException {
		if (createdProject == null) {
			throw new IllegalStateException("trying to delete test from disk without first creating it");
		}
		deleteTestFromDiskIfCreated();
	}

	/** Same as {@link #deleteTestFromDisk()}, but does nothing if no test workspace has been created yet. */
	public void deleteTestFromDiskIfCreated() throws IOException {
		if (createdProject != null) {
			File root = getRoot();
			FileUtils.delete(root);

			createdProject = null;
		}
	}

	/**
	 * Converts from pairs to a corresponding map. This method has two return values:
	 * <ol>
	 * <li>the resulting map, which is returned by changing the given argument <code>addHere</code>, and
	 * <li>iff a module in the given pairs is marked as selected module (see {@link #MODULE_SELECTOR}), then this method
	 * returns a pair "selected project path" -&gt; "selected module"; otherwise it returns <code>null</code>.
	 * </ol>
	 */
	/* package */ static Pair<String, String> convertProjectsModulesContentsToMap(
			Iterable<? extends Pair<String, ? extends Iterable<Pair<String, String>>>> projectsModulesContentsAsPairs,
			Map<String, Map<String, String>> addHere,
			boolean requireSelectedModule) {

		String selectedProjectPath = null;
		String selectedModule = null;
		addHere.clear();
		for (Pair<String, ? extends Iterable<Pair<String, String>>> project : projectsModulesContentsAsPairs) {
			String projectPath = project.getKey();
			Iterable<? extends Pair<String, String>> modules = project.getValue();
			Map<String, String> modulesMap = null;
			if (modules != null) {
				modulesMap = new HashMap<>();
				for (Pair<String, String> moduleContent : modules) {
					String moduleName = moduleContent.getKey();
					if (moduleName.endsWith(MODULE_SELECTOR)) {
						moduleName = moduleName.substring(0, moduleName.length() - 1);
						selectedProjectPath = projectPath;
						selectedModule = moduleName;
					}
					modulesMap.put(moduleName, moduleContent.getValue());
				}
			}
			addHere.put(projectPath, modulesMap);
		}

		if (requireSelectedModule && selectedModule == null) {
			throw new IllegalArgumentException(
					"No module selected. Fix by appending '" + MODULE_SELECTOR + "' to one of the project modules.");
		}

		return selectedModule != null ? Pair.of(selectedProjectPath, selectedModule) : null;
	}
}
