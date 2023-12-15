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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import org.eclipse.n4js.N4JSGlobals;

/**
 * Generates code for a module containing imports and either given classifiers or contents.
 */
public class OtherFile {
	/** The file name without extension. */
	final protected String name;
	/**
	 * The file extension. Will be <code>null</code> for files without extension and empty string for files with a name
	 * ending in ".".
	 */
	final protected String fExtension;
	/** File name provided using the {@code from=""} syntax or <code>null</code> if not provided. */
	final protected String fromFileName;
	protected String content;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the module name without extension
	 */
	public OtherFile(String name) {
		this(name, N4JSGlobals.N4JS_FILE_EXTENSION, null);
	}

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the module name without extension
	 */
	public OtherFile(String name, String fExtension, String fromFileName) {
		this.name = Objects.requireNonNull(name);
		this.fExtension = fExtension;
		this.fromFileName = fromFileName;
	}

	/**
	 * Returns the name of this module.
	 *
	 * @return the name of this module
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the file extension of this module. Will be <code>null</code> for files without extension and the empty
	 * string for files with a name ending in ".".
	 *
	 * @return the file extension of this module or <code>null</code>.
	 */
	public String getExtension() {
		return fExtension;
	}

	/** @return filename with extension (if any). */
	public String getNameWithExtension() {
		return fExtension != null ? name + "." + fExtension : name;
	}

	/** File name provided using the {@code from=""} syntax or <code>null</code> if not provided. */
	public String getFromFileName() {
		return fromFileName;
	}

	/**
	 * Sets the given string of contents to the module built by this builder. This will cause the classifiers to be
	 * ignored.
	 *
	 * @param contents
	 *            the contents to add
	 */
	public OtherFile setContents(String contents) {
		this.content = contents;
		return this;
	}

	/**
	 * Returns the contents of this module.
	 *
	 * @return the contents of this module
	 */
	public String getContents() {
		return content;
	}

	/**
	 * Creates this module as a file in the given parent directory, which must already exist.
	 *
	 * @param parentDirectory
	 *            a file representing the parent directory
	 */
	public void create(File parentDirectory) throws IOException {
		Objects.requireNonNull(parentDirectory);
		if (!parentDirectory.exists()) {
			throw new IOException("Directory '" + parentDirectory + "' does not exist");
		}
		if (!parentDirectory.isDirectory()) {
			throw new IOException("'" + parentDirectory + "' is not a directory");
		}

		File filePath = new File(parentDirectory, this.getNameWithExtension().replace('/', File.separatorChar));
		filePath.getParentFile().mkdirs();

		FileWriter out = null;
		try {
			out = new FileWriter(filePath);
			out.write(generate().toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Generates the N4JS code for this module.
	 */
	public String generate() {
		return content;
	}
}
