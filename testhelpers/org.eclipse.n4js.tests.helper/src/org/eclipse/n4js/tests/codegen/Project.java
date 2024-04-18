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
package org.eclipse.n4js.tests.codegen;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Strings;

/**
 * Generates the code for a project.
 */
public class Project {
	final String projectName;
	final String vendorId;
	final String vendorName;
	final LinkedHashSet<Folder> folders = new LinkedHashSet<>();
	final Set<String> projectDependencies = new LinkedHashSet<>();
	final Map<String, String> scripts = new LinkedHashMap<>();
	final Map<String, Project> nodeModuleProjects = new HashMap<>();
	ProjectType projectType;
	String projectVersion = "1.0.0";
	String mainModule = null;
	String outputFolder = "src-gen";
	String projectDescriptionContent = null;
	boolean generateDts = false;

	/**
	 * Same as {@link #Project(String, String, String, ProjectType)}, but with a default project type of
	 * {@link ProjectType#LIBRARY LIBRARY}.
	 */
	public Project(String projectName, String vendorId, String vendorName) {
		this(projectName, vendorId, vendorName, ProjectType.LIBRARY);
	}

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param projectName
	 *            the project ID
	 * @param vendorId
	 *            the vendor ID
	 * @param vendorName
	 *            the vendor name
	 * @param projectType
	 *            the project type
	 */
	public Project(String projectName, String vendorId, String vendorName, ProjectType projectType) {
		this.projectName = Objects.requireNonNull(projectName);
		this.vendorId = Objects.requireNonNull(vendorId);
		this.vendorName = Objects.requireNonNull(vendorName);
		this.projectType = Objects.requireNonNull(projectType);
	}

	/**
	 * Returns the project name.
	 *
	 * @return the project name.
	 */
	public String getName() {
		return projectName;
	}

	/**
	 * Returns the project type.
	 *
	 * @return the project type.
	 */
	public ProjectType getType() {
		return projectType;
	}

	/**
	 * Sets the project type.
	 *
	 * @param projectType
	 *            the project type to set
	 */
	public Project setType(ProjectType projectType) {
		this.projectType = projectType;
		return this;
	}

	/**
	 * Gets the project version.
	 *
	 * @return projectVersion the project
	 */
	public String getVersion() {
		return this.projectVersion;
	}

	/**
	 * Sets the project version.
	 *
	 * @param projectVersion
	 *            the project version
	 */
	public Project setVersion(String projectVersion) {
		this.projectVersion = projectVersion;
		return this;
	}

	/**
	 * Gets the project's main module.
	 *
	 * @return main module of the project.
	 */
	public String getMainModule() {
		return this.mainModule;
	}

	/**
	 * Sets the project's main module.
	 *
	 * @param mainModule
	 *            the main module.
	 */
	public Project setMainModule(String mainModule) {
		this.mainModule = mainModule;
		return this;
	}

	/**
	 * Returns the output folder.
	 *
	 * @return the output folder.
	 */
	public String getOutputFolder() {
		return outputFolder;
	}

	/**
	 * Sets the output folder.
	 *
	 * @param outputFolder
	 *            the output folder to set
	 */
	public Project setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
		return this;
	}

	/**
	 * @return true iff "generator"/"d.ts" is set to true.
	 */
	public boolean isGenerateDts() {
		return generateDts;
	}

	/**
	 * Turns on/off generation of .d.ts files.
	 */
	public Project setGenerateDts(boolean generateDts) {
		this.generateDts = generateDts;
		return this;
	}

	/**
	 * Sets the content of the project description file 'package.json'
	 *
	 * @param projectDescriptionContent
	 *            content of package.json
	 */
	public Project setProjectDescriptionContent(String projectDescriptionContent) {
		this.projectDescriptionContent = projectDescriptionContent;
		return this;
	}

	/**
	 * Returns content of package.json.
	 *
	 * @return content of package.json.
	 */
	public String getProjectDescriptionContent() {
		return projectDescriptionContent;
	}

	/**
	 * Creates a folder with the given name to this project.
	 *
	 * @param name
	 *            the name of the source folder to add
	 *
	 * @return the added source folder
	 */
	public Folder createFolder(String name) {
		Folder result = new Folder(name, false);
		addSourceFolder(result);
		return result;
	}

	/**
	 * Creates a source folder with the given name to this project.
	 *
	 * @param name
	 *            the name of the source folder to add
	 *
	 * @return the added source folder
	 */
	public Folder createSourceFolder(String name) {
		Folder result = new Folder(name, true);
		addSourceFolder(result);
		return result;
	}

	/**
	 * Adds a source folder to this project.
	 *
	 * @param sourceFolder
	 *            the source folder to add
	 */
	public Project addSourceFolder(Folder sourceFolder) {
		folders.add(Objects.requireNonNull(sourceFolder));
		return this;
	}

	/**
	 * Returns a list of all source folders of this project.
	 *
	 * @return list of all source folders of this project.
	 */
	public LinkedHashSet<Folder> getSourceFolders() {
		return folders;
	}

	/**
	 * Adds a project dependency to this project.
	 *
	 * @param projectDependency
	 *            the name of the project to add to the list of dependencies.
	 */
	public Project addProjectDependency(String projectDependency) {
		projectDependencies.add(Objects.requireNonNull(projectDependency));
		return this;
	}

	public void addScript(String name, String script) {
		scripts.put(name, script);
	}

	/**
	 * Returns a list of project dependencies of this project.
	 *
	 * @return projectDependencies the project
	 */
	public Set<String> getProjectDependencies() {
		return this.projectDependencies;
	}

	public void addNodeModuleProject(Project project) {
		this.nodeModuleProjects.put(project.projectName, project);
	}

	public Project getNodeModuleProject(String _projectName) {
		return this.nodeModuleProjects.get(_projectName);
	}

	public Collection<Project> getNodeModuleProjects() {
		List<Project> projects = new ArrayList<>();
		projects.addAll(nodeModuleProjects.values());
		return projects;
	}

	/**
	 * Generates the {@link N4JSGlobals#PACKAGE_JSON} for this project.
	 */
	public String generate() {
		if (!Strings.isNullOrEmpty(projectDescriptionContent)) {
			return projectDescriptionContent;
		}

		String scriptsStr = "";
		if (!scriptsStr.isEmpty()) {
			scriptsStr = join(",\n\t", e -> "\"%s\": \"%s\"".formatted(e.getKey(), e.getValue()), scripts.entrySet());
		}

		String vendorIdStr = "\"vendorId\": \"%s\"".formatted(vendorId);
		String vendorNameStr = "\"vendorName\": \"%s\"".formatted(vendorName);
		String projectTypeStr = "\"projectType\": \"%s\"".formatted(projectTypeToString(projectType));
		String mainModuleStr = mainModule == null ? "" : "\"mainModule\": \"%s\"".formatted(mainModule);
		String outputFolderStr = outputFolder == null ? "" : "\"output\": \"%s\"".formatted(outputFolder);
		String generateDtsStr = generateDts ? "\"generator\": { \"d.ts\": true }".formatted(outputFolder) : "";
		String sourcesStr = "";
		if (!folders.isEmpty()) {
			sourcesStr += "\"sources\": {\n\t\t\t\"source\": [";
			boolean isFirst = true;
			for (Folder sourceFolder : filter(folders, f -> f.isSourceFolder)) {
				if (!isFirst) {
					sourcesStr += ", ";
				}
				sourcesStr += "\"%s\"".formatted(sourceFolder.name);
				isFirst = false;
			}
			sourcesStr += "]\n\t\t}";
		}

		String n4jsProps = "";
		boolean isFirst = true;
		for (String prop : List.of(vendorIdStr, vendorNameStr, projectTypeStr, mainModuleStr, outputFolderStr,
				generateDtsStr, sourcesStr)) {

			if (!Strings.isNullOrEmpty(prop)) {
				if (!isFirst) {
					n4jsProps += ",\n\t\t";
				}
				n4jsProps += prop;
				isFirst = false;
			}
		}

		String deps = "";
		if (!projectDependencies.isEmpty()) {
			deps = join(",\n\t", d -> "\"%s\": \"\"".formatted(d), projectDependencies);
		}

		String result = """
				{
					"name": "%s",
					"version": "%s",
					"type": "module",
					"scripts": {%s},
					"n4js": {
						%s
					},
					"dependencies": {%s}
				}
				""".formatted(
				projectName,
				projectVersion,
				scriptsStr,
				n4jsProps,
				deps);

		return result;
	}

	private static String projectTypeToString(ProjectType type) {
		switch (type) {
		case API:
			return "api";
		case APPLICATION:
			return "application";
		case LIBRARY:
			return "library";
		case PROCESSOR:
			return "processor";
		case RUNTIME_ENVIRONMENT:
			return "runtimeEnvironment";
		case RUNTIME_LIBRARY:
			return "runtimeLibrary";
		case TEST:
			return "test";
		case PLAINJS:
			return "plainjs";
		case VALIDATION:
			return "validation";
		case DEFINITION:
			return "definition";
		}
		return "";
	}

	/**
	 * Creates this project in the given parent directory, which must exist.
	 *
	 * This method first creates a directory with the same name as the {@link #projectName} within the given parent
	 * directory. If there already exists a file or directory with that name within the given parent directory, that
	 * file or directory will be (recursively) deleted.
	 *
	 * Afterward, the package.json file and the source folders are created within the newly created project directory.
	 *
	 * @param parentDirectoryPath
	 *            the path to the parent directory
	 *
	 * @return the project directory
	 */
	public File create(Path parentDirectoryPath) throws IOException {
		File parentDirectory = Objects.requireNonNull(parentDirectoryPath).toFile();
		if (!parentDirectory.exists()) {
			throw new IOException("'" + parentDirectory + "' does not exist");
		}
		if (!parentDirectory.isDirectory()) {
			throw new IOException("'" + parentDirectory + "' is not a directory");
		}

		File projectDirectory = new File(parentDirectory, projectName);
		rmkdirs(projectDirectory);

		createProjectDescriptionFile(projectDirectory);
		createModules(projectDirectory);

		if (!nodeModuleProjects.isEmpty()) {
			File nodeModulesDirectory = new File(projectDirectory, N4JSGlobals.NODE_MODULES);
			if (nodeModulesDirectory.exists()) {
				FileDeleter.delete(nodeModulesDirectory);
			}
			nodeModulesDirectory.mkdir();
			createNodeModuleProjects(nodeModulesDirectory);
		}

		return projectDirectory;
	}

	private void createProjectDescriptionFile(File parentDirectory) throws IOException {
		File filePath = new File(parentDirectory, N4JSGlobals.PACKAGE_JSON);
		FileWriter out = null;
		try {
			out = new FileWriter(filePath);
			out.write(generate().toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private void createModules(File parentDirectory) throws IOException {
		for (Folder sourceFolder : folders) {
			sourceFolder.create(parentDirectory);
		}
	}

	private void createNodeModuleProjects(File parentDirectory) throws IOException {
		for (Project nodeModuleProject : nodeModuleProjects.values()) {
			nodeModuleProject.create(parentDirectory.toPath());
		}
	}

	void rmkdirs(File file) throws IOException {
		if (file.exists()) {
			FileDeleter.delete(file);
		}
		file.mkdirs();
	}
}
