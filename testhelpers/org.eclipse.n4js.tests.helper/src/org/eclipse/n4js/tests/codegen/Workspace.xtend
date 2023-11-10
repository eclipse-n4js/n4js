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
package org.eclipse.n4js.tests.codegen

import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.util.List
import java.util.Objects
import org.eclipse.n4js.utils.io.FileDeleter

/**
 * Generates code for a workspace.
 */
class Workspace {
	final List<Project> projects = newArrayList();

	def addProject(Project project) {
		this.projects.add(project);
	}

	def clearProjects() {
		this.projects.clear();
	}

	def List<Project> getProjects() {
		return this.projects;
	}

	/**
	 * Similar to {@link #getProjects()}, but also includes the member projects of {@link YarnWorkspaceProject}s.
	 */
	def Iterable<Project> getAllProjects() {
		return this.projects.flatMap[p|
			if (p instanceof YarnWorkspaceProject)
				#[p] + p.memberProjects + p.nodeModuleProjects
			else
				#[p]
		];
	}
	
	def void simplifyIfPossible() {
		if (isYarnToSingleProjectConvertable()) {
			val YarnWorkspaceProject yarnWorkspaceProject = getProjects().get(0) as YarnWorkspaceProject;
			val Project project = yarnWorkspaceProject.getMemberProjects().iterator().next();
			clearProjects();
			addProject(project);
		}
	}
	
	def boolean isYarnToSingleProjectConvertable() {
		return getProjects().size() == 1
				&& getAllProjects().size() == 2 // this includes the yarn project and the single project in packages
				&& getProjects().get(0) instanceof YarnWorkspaceProject
				&& (getProjects().get(0) as YarnWorkspaceProject).getMemberProjects().size() == 1;
	}

	public def File create(Path parentDirectoryPath) {
		var File wsDirectory = Objects.requireNonNull(parentDirectoryPath).toFile
		if (!wsDirectory.exists)
			throw new IOException("'" + wsDirectory + "' does not exist")
		if (!wsDirectory.directory)
			throw new IOException("'" + wsDirectory + "' is not a directory");

		if (wsDirectory.exists)
			FileDeleter.delete(wsDirectory);
		wsDirectory.mkdirs();

		for (project : projects)
			project.create(wsDirectory.toPath());

		return wsDirectory;
	}
}
