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
 *
 */
public class WorkspaceBuilder {

	abstract public class NamedEntityBuilder {
		public String name;

		public <T> T getBuilderInfo() {
			return (T) builderInfo;
		}
	}

	public class ProjectBuilder extends NamedEntityBuilder {
		String vendor = "VENDOR";
		String vendorName = "VENDOR_NAME";
		Map<String, FolderBuilder> folders = new LinkedHashMap<>();

		/** 	 */
		public FolderBuilder getOrAddFolder(String path) {
			if (folders.containsKey(path)) {
				return folders.get(path);
			}
			return addFolder(path);
		}

		/**  */
		public FolderBuilder addFolder(String folderName) {
			FolderBuilder folderBuilder = new FolderBuilder();
			folderBuilder.name = folderName;
			folders.put(folderName, folderBuilder);
			return folderBuilder;
		}

		/**  */
		public Project build() {
			Project project = new Project(name, vendor, vendorName);
			for (FolderBuilder folderBuilder : folders.values()) {
				project.addSourceFolder(folderBuilder.build());
			}
			return project;
		}
	}

	public class FolderBuilder extends NamedEntityBuilder {
		Map<String, ModuleBuilder> modules = new LinkedHashMap<>();
		Map<String, OtherFileBuilder> files = new LinkedHashMap<>();
		public boolean isSourceFolder;

		/**  */
		public OtherFileBuilder addFile(String nameWithExtension, String content) {
			int idx = nameWithExtension.lastIndexOf(".");
			String name = nameWithExtension.substring(0, idx);
			String extension = nameWithExtension.substring(idx + 1);
			return addFile(name, extension, content);
		}

		/**  */
		public OtherFileBuilder addFile(String name, String fExtension, String content) {
			OtherFileBuilder fileBuilder = new OtherFileBuilder();
			fileBuilder.name = name;
			fileBuilder.fExtension = fExtension;
			fileBuilder.content = content;
			files.put(name, fileBuilder);
			return fileBuilder;
		}

		public ModuleBuilder addModule(String name, String fExtension, String content) {
			ModuleBuilder moduleBuilder = new ModuleBuilder();
			moduleBuilder.name = name;
			moduleBuilder.fExtension = fExtension;
			moduleBuilder.content = content;
			modules.put(name, moduleBuilder);
			return moduleBuilder;
		}

		/**  */
		public Folder build() {
			Folder folder = new Folder(name);
			for (ModuleBuilder moduleBuilder : modules.values()) {
				folder.modules.add(moduleBuilder.build());
			}
			for (OtherFileBuilder fileBuilder : files.values()) {
				folder.files.add(fileBuilder.build());
			}
			return folder;
		}

		public class OtherFileBuilder extends NamedEntityBuilder {
			public String fExtension;
			public String content;

			/**  */
			public OtherFile build() {
				OtherFile file = new OtherFile(name, fExtension);
				file.content = content;
				return file;
			}

			public FolderBuilder getFolderBuilder() {
				return FolderBuilder.this;
			}
		}

		public class ModuleBuilder extends OtherFileBuilder {

			/**  */
			@Override
			public Module build() {
				Module module = new Module(name, fExtension);
				module.content = content;
				return module;
			}
		}
	}

	Map<String, ProjectBuilder> projectBuilders = new LinkedHashMap<>();
	public Object builderInfo;

	public WorkspaceBuilder() {
		this(null);
	}

	public WorkspaceBuilder(Object builderInfo) {
		this.builderInfo = builderInfo;
	}

	/**
	 */
	public ProjectBuilder addProject(String projectName) {
		ProjectBuilder prjBuilder = new ProjectBuilder();
		prjBuilder.name = projectName;
		projectBuilders.put(projectName, prjBuilder);
		return prjBuilder;
	}

	/**  */
	public Workspace build() {
		return build(new Workspace());
	}

	/**  */
	public <W extends Workspace> W build(W workspace) {
		for (ProjectBuilder projectBuilder : projectBuilders.values()) {
			workspace.addProject(projectBuilder.build());
		}
		return workspace;
	}

}
