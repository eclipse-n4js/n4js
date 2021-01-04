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
import java.nio.file.Path
import java.util.Collection
import java.util.Map
import java.util.Objects
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.utils.Strings
import org.eclipse.n4js.utils.io.FileDeleter

/**
 * Generates the code for a yarn workspace project.
 */
public class YarnWorkspaceProject extends Project {

	/** Name of the 'packages' folder, i.e. the folder containing the actual projects. */
	public static final String PACKAGES = "packages";

	final Map<String, Map<String, Project>> memberProjects = newLinkedHashMap();

	/**
	 * Same as {@link #Project(String, String, String, ProjectType)}, but with
	 * a default project type of {@link ProjectType#PLAINJS PLAINJS}.
	 */
	public new(String projectName, String vendorId, String vendorName) {
		this(projectName, vendorId, vendorName, PACKAGES);
	}

	/**
	 * Same as {@link #Project(String, String, String, ProjectType)}, but with
	 * a default project type of {@link ProjectType#PLAINJS PLAINJS}.
	 */
	public new(String projectName, String vendorId, String vendorName, String workspacesFolderName) {
		super(projectName, vendorId, vendorName, ProjectType.PLAINJS);
		this.memberProjects.put(workspacesFolderName, newHashMap());
	}

	public def void addWorkspaceName(String workspacesFolderName) {
		this.memberProjects.put(workspacesFolderName, newHashMap());
	}

	public def void addMemberProject(Project project) {
		addMemberProject(memberProjects.keySet.iterator.next, project);
	}

	public def void addMemberProject(String workspaceFolderName, Project project) {
		this.memberProjects.get(workspaceFolderName).put(project.name, project);
	}

	public def Collection<Project> getMemberProjects() {
		val projects = newArrayList();
		for (name2prj : memberProjects.values)
			projects.addAll(name2prj.values);
		return projects;
	}

	public def Project getMemberProject(String projectName) {
		getMemberProject(memberProjects.keySet.iterator.next, projectName);
	}

	public def Project getMemberProject(String workspaceFolderName, String projectName) {
		return this.memberProjects.get(workspaceFolderName).get(projectName);
	}


	/**
	 * Generates the {@link IN4JSProject#PACKAGE_JSON} for this project.
	 */
	public override String generate() '''
		{
			"name": "«name»",
			"version": "«version»",
			"private": true,
			"workspaces": [
			    "«Strings.join(", ", [wsName | wsName + "/*"], memberProjects.keySet())»"
		    ],
			"dependencies": {
					«IF !projectDependencies.nullOrEmpty»
					«FOR dep : projectDependencies SEPARATOR ','»
						"«dep»": "*"
					«ENDFOR»
					«ENDIF»
			}
		}
	'''


	/**
	 * Creates this project in the given parent directory, which must exist.
	 *
	 * This method first creates a directory with the same name as the {@link #projectName} within
	 * the given parent directory. If there already exists a file or directory with that name
	 * within the given parent directory, that file or directory will be (recursively) deleted.
	 *
	 * Afterward, the package.json file and the source folders are created within the newly created
	 * project directory.
	 *
	 * @param parentDirectoryPath the path to the parent directory
	 *
	 * @return the project directory
	 */
	public override File create(Path parentDirectoryPath) {
		super.create(parentDirectoryPath);

		var File parentDirectory = Objects.requireNonNull(parentDirectoryPath).toFile
		val File projectDirectory = new File(parentDirectory, name);
		
		for (workspacesFolderName : memberProjects.keySet()) {
			val File workspacesDirectory = new File(new File(parentDirectory, name), workspacesFolderName);
			if (workspacesDirectory.exists)
				FileDeleter.delete(workspacesDirectory);
			workspacesDirectory.mkdirs();
	
			createWorkspaceProjects(memberProjects.get(workspacesFolderName), workspacesDirectory);
		}

		return projectDirectory;
	}

	private def void createWorkspaceProjects(Map<String, Project> name2projects, File parentDirectory) {
		for (project: name2projects.values())
			project.create(parentDirectory.toPath);
	}
}
