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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a source folder that has a name and contains modules.
 */
public class Folder {
	final public String name;
	final public List<Module> modules = new LinkedList<>();
	final public List<OtherFile> files = new LinkedList<>();
	final public boolean isSourceFolder;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the name of this source folder
	 */
	public Folder(String name) {
		this(name, true);
	}

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the name of this source folder
	 * @param isSourceFolder
	 *            iff true it will be listed as a source folder in the package.json
	 */
	public Folder(String name, boolean isSourceFolder) {
		this.name = Objects.requireNonNull(name);
		this.isSourceFolder = isSourceFolder;
	}

	/**
	 * Adds the given module to the source folder to created.
	 *
	 * @param module
	 *            the module to add
	 *
	 * @return this source folder
	 */
	public Folder addModule(Module module) {
		modules.add(Objects.requireNonNull(module));
		return this;
	}

	/**
	 * Returns a list of all modules of this source folder.
	 *
	 * @return list of all modules of this source folder
	 */
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * Returns a list of all non-modules of this folder.
	 *
	 * @return list of all non-modules of this folder
	 */
	public List<OtherFile> getOtherFiles() {
		return files;
	}

	/**
	 * Creates this source folder within the given parent directory, which must exist.
	 *
	 * This method first creates a new folder within the given parent directory, and then it creates all of its modules
	 * within that folder by calling their {@link Module#create(File)} function with the newly created folder as the
	 * parameter.
	 *
	 * @param parentDirectory
	 *            a file representing the parent directory of this source folder
	 */
	public void create(File parentDirectory) throws IOException {
		Objects.requireNonNull(parentDirectory);
		if (!parentDirectory.exists()) {
			throw new IOException("Directory '" + parentDirectory + "' does not exist");
		}
		if (!parentDirectory.isDirectory()) {
			throw new IOException("'" + parentDirectory + "' is not a directory");
		}

		File folder = new File(parentDirectory, name);
		folder.mkdirs();

		for (Module module : modules) {
			module.create(folder);
		}
		for (OtherFile file : files) {
			file.create(folder);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Folder) {
			Objects.equals(name, ((Folder) o).name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
