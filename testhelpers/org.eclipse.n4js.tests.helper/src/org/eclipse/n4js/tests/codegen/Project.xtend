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
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.util.List
import java.util.Map
import java.util.Objects
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.utils.io.FileDeleter

/**
 * Generates the code for a project.
 */
public class Project {

	/**
	 * Represents a source folder that has a name and contains modules.
	 */
	public static class SourceFolder {
		final String name;
		final List<Module> modules = newLinkedList();

		/**
		 * Creates a new instance with the given parameters.
		 *
		 * @param name the name of this source folder
		 */
		public new(String name) {
			this.name = Objects.requireNonNull(name);
		}


		/**
		 * Adds the given module to the source folder to created.
		 *
		 * @param module the module to add
		 *
		 * @return this source folder
		 */
		public def addModule(Module module) {
			modules.add(Objects.requireNonNull(module));
			return this;
		}

		/**
		 * Returns a list of all modules of this source folder.
		 *
		 * @return list of all modules of this source folder
		 */
		public def List<Module> getModules() {
			return modules;
		}

		/**
		 * Creates this source folder within the given parent directory, which must exist.
		 *
		 * This method first creates a new folder within the given parent directory, and then
		 * it creates all of its modules within that folder by calling their {@link Module#create(File)}
		 * function with the newly created folder as the parameter.
		 *
		 * @param parentDirectory a file representing the parent directory of this source folder
		 */
		public def create(File parentDirectory) {
			Objects.requireNonNull(parentDirectory);
			if (!parentDirectory.exists)
				throw new IOException("Directory '" + parentDirectory + "' does not exist");
			if (!parentDirectory.directory)
				throw new IOException("'" + parentDirectory + "' is not a directory");

			var File sourceFolder = new File(parentDirectory, name);
			sourceFolder.mkdir();

			for (module: modules)
				module.create(sourceFolder)
		}
	}

	final String projectName;
	final String vendorId;
	final String vendorName;
	final List<SourceFolder> sourceFolders = newLinkedList();
	final List<Project> projectDependencies = newLinkedList();
	final Map<String, Project> nodeModuleProjects = newHashMap();
	ProjectType projectType;
	String projectVersion = "1.0.0";
	String mainModule = null;
	String outputFolder = "src-gen";
	String projectDescriptionContent = null;

	/**
	 * Same as {@link #Project(String, String, String, ProjectType)}, but with
	 * a default project type of {@link ProjectType#LIBRARY LIBRARY}.
	 */
	public new(String projectName, String vendorId, String vendorName) {
		this(projectName, vendorId, vendorName, ProjectType.LIBRARY);
	}

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param projectName the project ID
	 * @param vendorId the vendor ID
	 * @param vendorName the vendor name
	 * @param projectType the project type
	 */
	public new(String projectName, String vendorId, String vendorName, ProjectType projectType) {
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
	public def String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the project type.
	 *
	 * @param projectType the project type to set
	 */
	public def Project setType(ProjectType projectType) {
		this.projectType = projectType;
		return this;
	}

	/**
	 * Gets the project version.
	 *
	 * @return projectVersion the project
	 */
	public def String getVersion() {
		return this.projectVersion;
	}

	/**
	 * Sets the project version.
	 *
	 * @param projectVersion the project version
	 */
	public def Project setVersion(String projectVersion) {
		this.projectVersion = projectVersion;
		return this;
	}

	/**
	 * Gets the project's main module.
	 *
	 * @return main module of the project.
	 */
	public def String getMainModule() {
		return this.mainModule;
	}

	/**
	 * Sets the project's main module.
	 *
	 * @param mainModule the main module.
	 */
	public def Project setMainModule(String mainModule) {
		this.mainModule = mainModule;
		return this;
	}

	/**
	 * Returns the output folder.
	 * 
	 * @return the output folder.
	 */
	public def String getOutputFolder() {
		return outputFolder;
	}

	/**
	 * Sets the output folder.
	 *
	 * @param outputFolder the output folder to set
	 */
	public def Project setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
		return this;
	}

	/**
	 * Sets the content of the project description file 'package.json'
	 * 
	 * @param projectDescriptionContent content of package.json
	 */
	public def Project setProjectDescriptionContent(String projectDescriptionContent) {
		this.projectDescriptionContent = projectDescriptionContent;
		return this;
	}

	/**
	 * Returns content of package.json.
	 * 
	 * @return content of package.json.
	 */
	public def String getProjectDescriptionContent() {
		return projectDescriptionContent;
	}

	/**
	 * Creates a source folder with the given name to this project.
	 *
	 * @param name the name of the source folder to add
	 *
	 * @return the added source folder
	 */
	public def SourceFolder createSourceFolder(String name) {
		val SourceFolder result = new SourceFolder(name);
		addSourceFolder(result);
		return result;
	}


	/**
	 * Adds a source folder to this project.
	 *
	 * @param sourceFolder the source folder to add
	 */
	public def Project addSourceFolder(SourceFolder sourceFolder) {
		sourceFolders.add(Objects.requireNonNull(sourceFolder));
		return this;
	}


	/**
	 * Returns a list of all source folders of this project.
	 *
	 * @return list of all source folders of this project.
	 */
	public def List<SourceFolder> getSourceFolders() {
		return sourceFolders;
	}

	/**
	 * Adds a project dependency to this project.
	 *
	 * @param projectDependency the project dependency to add
	 */
	public def Project addProjectDependency(Project projectDependency) {
		projectDependencies.add(Objects.requireNonNull(projectDependency));
		return this;
	}

	/**
	 * Returns a list of project dependencies of this project.
	 *
	 * @return projectDependencies the project
	 */
	public def List<Project> getProjectDependencies() {
		return this.projectDependencies;
	}
	
	public def void addNodeModuleProject(Project project) {
		this.nodeModuleProjects.put(project.projectName, project);
		this.addProjectDependency(project);
	}
	
	public def Project getNodeModuleProject(String projectName) {
		return this.nodeModuleProjects.get(projectName);
	}
	
	/**
	 * Generates the {@link IN4JSProject#PACKAGE_JSON} for this project.
	 */
	public def String generate() '''
		«IF !projectDescriptionContent.nullOrEmpty»«
			projectDescriptionContent»
		«ELSE»
		{
			"name": "«projectName»",
			"version": "«projectVersion»",
			"n4js": {
				"vendorId": "«vendorId»",
				"vendorName": "«vendorName»",
				"projectType": "«projectType.projectTypeToString»"
				«IF mainModule !== null
					»,"mainModule": "«mainModule»"
				«ENDIF»
				«IF !outputFolder.nullOrEmpty
					»,"output": "«outputFolder»"
				«ENDIF»
				«IF !sourceFolders.nullOrEmpty
				»,"sources": {
						"source": [
							«FOR sourceFolder : sourceFolders SEPARATOR ','»
								"«sourceFolder.name»"
							«ENDFOR»
						]
					}
				«ENDIF»
			},
			"dependencies": {
					«IF !projectDependencies.nullOrEmpty»
					«FOR dep : projectDependencies SEPARATOR ','»
						"«dep.projectName»": "*"
					«ENDFOR»
					«ENDIF»
				}
		}
		«ENDIF»
	'''

	private static def String projectTypeToString(ProjectType type) {
		return switch (type) {
			case API: "api"
			case APPLICATION: "application"
			case LIBRARY: "library"
			case PROCESSOR: "processor"
			case RUNTIME_ENVIRONMENT: "runtimeEnvironment"
			case RUNTIME_LIBRARY: "runtimeLibrary"
			case TEST: "test"
			case PLAINJS: "plainjs"
			case VALIDATION: "validation"
			case DEFINITION: "definition"
		};
	}

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
	public def File create(Path parentDirectoryPath) {
		var File parentDirectory = Objects.requireNonNull(parentDirectoryPath).toFile
		if (!parentDirectory.exists)
			throw new IOException("'" + parentDirectory + "' does not exist")
		if (!parentDirectory.directory)
			throw new IOException("'" + parentDirectory + "' is not a directory");

		val File projectDirectory = new File(parentDirectory, projectName);
		if (projectDirectory.exists)
			FileDeleter.delete(projectDirectory);
		projectDirectory.mkdirs();

		createProjectDescriptionFile(projectDirectory);
		createModules(projectDirectory);
		
		if (!nodeModuleProjects.isEmpty()) {
			val File nodeModulesDirectory = new File(projectDirectory, N4JSGlobals.NODE_MODULES);
			if (nodeModulesDirectory.exists)
				FileDeleter.delete(nodeModulesDirectory);
			nodeModulesDirectory.mkdir();
			createNodeModuleProjects(nodeModulesDirectory);
		}

		return projectDirectory;
	}

	private def void createProjectDescriptionFile(File parentDirectory) {
		val File filePath = new File(parentDirectory, IN4JSProject.PACKAGE_JSON);
		var FileWriter out = null;
		try {
			out = new FileWriter(filePath);
			out.write(generate().toString());
		} finally {
			if (out !== null)
				out.close();
		}
	}

	private def void createModules(File parentDirectory) {
		for (sourceFolder: sourceFolders)
			sourceFolder.create(parentDirectory);
	}

	private def void createNodeModuleProjects(File parentDirectory) {
		for (nodeModuleProject: nodeModuleProjects.values)
			nodeModuleProject.create(parentDirectory.toPath());
	}
}
