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

import java.util.List
import java.nio.file.Path
import java.io.File
import org.eclipse.xpect.setup.XpectSetupComponent
import java.util.Objects
import java.io.IOException
import org.eclipse.n4js.utils.io.FileDeleter

/**
 * Generates code for a workspace.
 */
@XpectSetupComponent
class Workspace {
	final List<Project> projects = newArrayList();

	def addProject(Project project) {
		this.projects.add(project);
	}
	
	def List<Project> getProjects() {
		return this.projects;
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
