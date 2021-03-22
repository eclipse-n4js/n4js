/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.codegen;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builder for {@link Workspace}
 */
public class WorkspaceBuilder {

	/** Superclass for all builder classes below */
	abstract public class NamedEntityBuilder {
		String name;

		NamedEntityBuilder(String name) {
			this.name = name;
		}

		/** @return the name of the entity */
		public String getName() {
			return name;
		}

		/** @return the builder info. Can be null. */
		@SuppressWarnings("unchecked")
		public <T> T getBuilderInfo() {
			return (T) builderInfo;
		}
	}

	/** Builder for {@link YarnWorkspaceProject} */
	public class YarnProjectBuilder extends ProjectBuilder {
		Map<String, ProjectBuilder> yarnProjectBuilders = new LinkedHashMap<>();

		YarnProjectBuilder(String name) {
			super(name);
		}

		/** Adds a project to the yarn workspace */
		public ProjectBuilder addProject(String projectName) {
			ProjectBuilder prjBuilder = new ProjectBuilder(projectName);
			yarnProjectBuilders.put(projectName, prjBuilder);
			return prjBuilder;
		}

		/** Builds the {@link YarnWorkspaceProject} */
		@Override
		public Project build() {
			YarnWorkspaceProject project = new YarnWorkspaceProject(name, vendor, vendorName);
			for (FolderBuilder folderBuilder : folders.values()) {
				project.addSourceFolder(folderBuilder.build());
			}
			for (ProjectBuilder prjBuilder : yarnProjectBuilders.values()) {
				project.addMemberProject(prjBuilder.build());
			}
			return project;
		}
	}

	/** Builder for {@link Project} */
	public class ProjectBuilder extends NamedEntityBuilder {
		String vendor = "VENDOR";
		String vendorName = "VENDOR_NAME";
		Map<String, FolderBuilder> folders = new LinkedHashMap<>();

		ProjectBuilder(String name) {
			super(name);
		}

		/** @return the folder for the given path. Creates a Folder iff it does not exist already. */
		public FolderBuilder getOrAddFolder(String path) {
			if (folders.containsKey(path)) {
				return folders.get(path);
			}
			return addFolder(path);
		}

		/** Adds a {@link Folder} */
		public FolderBuilder addFolder(String folderName) {
			FolderBuilder folderBuilder = new FolderBuilder(folderName);
			folders.put(folderName, folderBuilder);
			return folderBuilder;
		}

		/** Builds the {@link Project} */
		public Project build() {
			Project project = new Project(name, vendor, vendorName);
			for (FolderBuilder folderBuilder : folders.values()) {
				project.addSourceFolder(folderBuilder.build());
			}
			return project;
		}

		/** Builder for {@link Folder} */
		public class FolderBuilder extends NamedEntityBuilder {
			Map<String, ModuleBuilder> modules = new LinkedHashMap<>();
			Map<String, OtherFileBuilder> files = new LinkedHashMap<>();
			boolean isSourceFolder = false;

			FolderBuilder(String name) {
				super(name);
			}

			/** Sets this folder to be a source folder */
			public FolderBuilder setSourceFolder() {
				isSourceFolder = true;
				return this;
			}

			/**
			 * Adds a {@link OtherFile}.
			 *
			 * @param nameWithExtension
			 *            full name, including the file extension in case the file has an extension.
			 */
			public OtherFileBuilder addFile(String nameWithExtension, String content) {
				int idx = nameWithExtension.lastIndexOf(".", nameWithExtension.lastIndexOf('/') + 1);
				String nameWithoutExtension = idx >= 0 ? nameWithExtension.substring(0, idx) : nameWithExtension;
				String extension = idx >= 0 ? nameWithExtension.substring(idx + 1) : null;
				return addFile(nameWithoutExtension, extension, content);
			}

			/**
			 * Adds a {@link OtherFile}.
			 *
			 * @param fExtension
			 *            the file extension. Use <code>null</code> for files without extension and the empty string for
			 *            files with a name ending in ".".
			 */
			public OtherFileBuilder addFile(String fNameWithoutExtension, String fExtension, String content) {
				OtherFileBuilder fileBuilder = new OtherFileBuilder(fNameWithoutExtension);
				fileBuilder.fExtension = fExtension;
				fileBuilder.content = content;
				files.put(fileBuilder.getNameWithExtension(), fileBuilder);
				return fileBuilder;
			}

			/** Adds a {@link Module} */
			public ModuleBuilder addModule(String nameWithoutExtension, String fExtension, String content) {
				ModuleBuilder moduleBuilder = new ModuleBuilder(nameWithoutExtension);
				moduleBuilder.fExtension = fExtension;
				moduleBuilder.content = content;
				modules.put(nameWithoutExtension + "." + fExtension, moduleBuilder);
				return moduleBuilder;
			}

			/** Builds the {@link Folder} */
			public Folder build() {
				Folder folder = new Folder(name, isSourceFolder);
				for (ModuleBuilder moduleBuilder : modules.values()) {
					folder.modules.add(moduleBuilder.build());
				}
				for (OtherFileBuilder fileBuilder : files.values()) {
					folder.files.add(fileBuilder.build());
				}
				return folder;
			}

			/** @return the parent {@link ProjectBuilder} */
			public ProjectBuilder getProjectBuilder() {
				return ProjectBuilder.this;
			}

			/** Builder for {@link OtherFile} */
			public class OtherFileBuilder extends NamedEntityBuilder {
				/**
				 * The file extension. Will be <code>null</code> for files without extension and empty string for files
				 * with a name ending in ".".
				 */
				public String fExtension;
				/** File content */
				public String content;

				OtherFileBuilder(String nameWithoutExtension) {
					super(nameWithoutExtension);
				}

				/** Builds the {@link OtherFile} */
				public OtherFile build() {
					OtherFile file = new OtherFile(name, fExtension);
					file.content = content;
					return file;
				}

				/** @return the parent {@link FolderBuilder} */
				public FolderBuilder getFolderBuilder() {
					return FolderBuilder.this;
				}

				/** @return filename with extension (if any) */
				public String getNameWithExtension() {
					return fExtension != null ? name + "." + fExtension : name;
				}

				/** @return path including file name */
				public String getPath() {
					String prjName = getFolderBuilder().getProjectBuilder().getName();
					String folderName = getFolderBuilder().getName();
					String path = prjName + "/" + folderName + "/" + getNameWithExtension();
					return path.replace("./", "");
				}
			}

			/** Builder for {@link Module} */
			public class ModuleBuilder extends OtherFileBuilder {

				ModuleBuilder(String nameWithoutExtension) {
					super(nameWithoutExtension);
				}

				/** Builds the {@link Module} */
				@Override
				public Module build() {
					Module module = new Module(name, fExtension);
					module.content = content;
					return module;
				}
			}
		}
	}

	Map<String, ProjectBuilder> projectBuilders = new LinkedHashMap<>();
	/** Object to be used for user information while building the workspace */
	public Object builderInfo;

	/** Constructor */
	public WorkspaceBuilder() {
		this(null);
	}

	/** Constructor. Sets the given builder info. */
	public WorkspaceBuilder(Object builderInfo) {
		this.builderInfo = builderInfo;
	}

	/** Adds a project to the workspace */
	public ProjectBuilder addProject(String projectName) {
		ProjectBuilder prjBuilder = new ProjectBuilder(projectName);
		projectBuilders.put(projectName, prjBuilder);
		return prjBuilder;
	}

	/** Adds a yarn project to the workspace */
	public YarnProjectBuilder addYarnProject(String projectName) {
		YarnProjectBuilder yarnPrjBuilder = new YarnProjectBuilder(projectName);
		projectBuilders.put(projectName, yarnPrjBuilder);
		return yarnPrjBuilder;
	}

	/** Builds this workspace */
	public Workspace build() {
		return build(new Workspace());
	}

	/** Builds this workspace using the given instance */
	public <W extends Workspace> W build(W workspace) {
		for (ProjectBuilder projectBuilder : projectBuilders.values()) {
			workspace.addProject(projectBuilder.build());
		}
		return workspace;
	}

}
