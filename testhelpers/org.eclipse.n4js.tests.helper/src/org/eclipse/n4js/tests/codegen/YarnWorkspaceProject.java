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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.Strings;

/**
 * Generates the code for a yarn workspace project.
 */
public class YarnWorkspaceProject extends Project {

	/** Name of the 'packages' folder, i.e. the folder containing the actual projects. */
	public static final String PACKAGES = "packages";

	final Map<String, Map<String, Project>> memberProjects = new LinkedHashMap<>();

	/**
	 * Same as {@link Project#Project(String, String, String, ProjectType)}, but with a default project type of
	 * {@link ProjectType#PLAINJS PLAINJS}.
	 */
	public YarnWorkspaceProject(String projectName, String vendorId, String vendorName) {
		this(projectName, vendorId, vendorName, PACKAGES);
	}

	/**
	 * Same as {@link Project#Project(String, String, String, ProjectType)}, but with a default project type of
	 * {@link ProjectType#PLAINJS PLAINJS}.
	 */
	public YarnWorkspaceProject(String projectName, String vendorId, String vendorName, String workspacesFolderName) {
		super(projectName, vendorId, vendorName, ProjectType.PLAINJS);
		this.memberProjects.put(workspacesFolderName, new HashMap<>());
	}

	public void addWorkspaceName(String workspacesFolderName) {
		this.memberProjects.put(workspacesFolderName, new HashMap<>());
	}

	public void addMemberProject(Project project) {
		addMemberProject(memberProjects.keySet().iterator().next(), project);
	}

	public void addMemberProject(String workspaceFolderName, Project project) {
		this.memberProjects.putIfAbsent(workspaceFolderName, new HashMap<>());
		this.memberProjects.get(workspaceFolderName).put(project.getName(), project);
	}

	public Collection<Project> getMemberProjects() {
		Collection<Project> projects = new ArrayList<>();
		for (Map<String, Project> name2prj : memberProjects.values()) {
			projects.addAll(name2prj.values());
		}
		return projects;
	}

	public Project getMemberProject(String _projectName) {
		return getMemberProject(memberProjects.keySet().iterator().next(), _projectName);
	}

	public Project getMemberProject(String workspaceFolderName, String _projectName) {
		return this.memberProjects.get(workspaceFolderName).get(_projectName);
	}

	/**
	 * Generates the {@link N4JSGlobals#PACKAGE_JSON} for this project.
	 */
	@Override
	public String generate() {
		if (!com.google.common.base.Strings.isNullOrEmpty(projectDescriptionContent)) {
			return projectDescriptionContent;
		}

		String workspaces = Strings.join(", ", ws -> "\"%s/*\"".formatted(ws), memberProjects.keySet());
		String deps = Strings.join(",\n", d -> "\"%s\": \"*\"".formatted(d), projectDependencies);

		return """
				{
					"name": "%s",
					"version": "%s",
					"private": true,
					"workspaces": [
						%s
				    ],
					"dependencies": {
						%s
					}
				}
				""".formatted(
				getName(),
				getVersion(),
				workspaces,
				deps);
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
	@Override
	public File create(Path parentDirectoryPath) throws IOException {
		super.create(parentDirectoryPath);

		File parentDirectory = Objects.requireNonNull(parentDirectoryPath).toFile();
		File projectDirectory = new File(parentDirectory, getName());
		File nodeModulesDirectory = new File(projectDirectory, N4JSGlobals.NODE_MODULES);
		nodeModulesDirectory.mkdirs();

		for (String workspacesFolderName : memberProjects.keySet()) {
			File workspacesDirectory = new File(new File(parentDirectory, getName()), workspacesFolderName);
			rmkdirs(workspacesDirectory);

			createWorkspaceProjects(memberProjects.get(workspacesFolderName), nodeModulesDirectory,
					workspacesDirectory);
		}

		return projectDirectory;
	}

	private void createWorkspaceProjects(Map<String, Project> name2projects, File nodeModulesDirectory,
			File parentDirectory) throws IOException {

		for (Project project : name2projects.values()) {
			File projectDir = project.create(parentDirectory.toPath());
			Path symProjectDirectory = new File(nodeModulesDirectory, project.getName()).toPath();
			symProjectDirectory.getParent().toFile().mkdir();
			Files.createSymbolicLink(symProjectDirectory, projectDir.toPath());
		}
	}
}
