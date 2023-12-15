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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.utils.io.FileDeleter;

/**
 * Generates code for a workspace.
 */
public class Workspace {
	final List<Project> projects = new ArrayList<>();

	public void addProject(Project project) {
		this.projects.add(project);
	}

	public void clearProjects() {
		this.projects.clear();
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	/**
	 * Similar to {@link #getProjects()}, but also includes the member projects of {@link YarnWorkspaceProject}s.
	 */
	public List<Project> getAllProjects() {
		List<Project> allProjects = new ArrayList<>();
		for (Project p : projects) {
			allProjects.add(p);
			if (p instanceof YarnWorkspaceProject) {
				YarnWorkspaceProject ywp = (YarnWorkspaceProject) p;
				allProjects.addAll(ywp.getMemberProjects());
				allProjects.addAll(ywp.getNodeModuleProjects());
			}
		}
		return allProjects;
	}

	public void simplifyIfPossible() {
		if (isYarnToSingleProjectConvertable()) {
			YarnWorkspaceProject yarnWorkspaceProject = (YarnWorkspaceProject) getProjects().get(0);
			Project project = yarnWorkspaceProject.getMemberProjects().iterator().next();
			clearProjects();
			addProject(project);
		}
	}

	public boolean isYarnToSingleProjectConvertable() {
		return getProjects().size() == 1
				&& getAllProjects().size() == 2 // this includes the yarn project and the single project in packages
				&& getProjects().get(0) instanceof YarnWorkspaceProject
				&& ((YarnWorkspaceProject) getProjects().get(0)).getMemberProjects().size() == 1;
	}

	public File create(Path parentDirectoryPath) throws IOException {
		File wsDirectory = Objects.requireNonNull(parentDirectoryPath).toFile();
		if (!wsDirectory.exists()) {
			throw new IOException("'" + wsDirectory + "' does not exist");
		}
		if (!wsDirectory.isDirectory()) {
			throw new IOException("'" + wsDirectory + "' is not a directory");
		}

		if (wsDirectory.exists()) {
			FileDeleter.delete(wsDirectory);
		}
		wsDirectory.mkdirs();

		for (Project project : projects) {
			project.create(wsDirectory.toPath());
		}

		return wsDirectory;
	}
}
