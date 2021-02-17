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
import java.util.List
import java.util.Objects

/**
 * Represents a source folder that has a name and contains modules.
 */
public class Folder {
	final public String name;
	final public List<Module> modules = newLinkedList();
	final public List<OtherFile> files = newLinkedList();
	final public boolean isSourceFolder = true;

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

		var File folder = new File(parentDirectory, name);
		folder.mkdir();

		for (module : modules)
			module.create(folder)
	}

	override equals(Object o) {
		if (o instanceof Folder) {
			Objects.equals(name, o.name);
		}
		return false;
	}

	override hashCode() {
		return name.hashCode();
	}
}
