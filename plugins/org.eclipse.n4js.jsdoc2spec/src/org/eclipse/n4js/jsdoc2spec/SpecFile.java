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
package org.eclipse.n4js.jsdoc2spec;

import java.io.File;
import java.util.Collection;

import org.eclipse.n4js.jsdoc2spec.adoc.SpecIdentifiableElementSection;
import org.eclipse.n4js.jsdoc2spec.adoc.SpecSection;

/**
 * A {@link SpecFile} contains one or more {@link SpecSection}s. {@link SpecFile} are used for any kind of file changes,
 * e.g. modules or index files.
 */
public abstract class SpecFile implements Comparable<SpecFile> {
	final String fileKey;
	final File file;

	/**
	 * Constructor
	 */
	public SpecFile(File file) {
		this.file = file;
		this.fileKey = file.toString();
	}

	/**
	 * Returns the file.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Returns the file key, i.e. the file name of this change file.
	 */
	public String getFileKey() {
		return fileKey;
	}

	/**
	 * Returns the name of the package, in case that this {@link SpecFile} contains a
	 * {@link SpecIdentifiableElementSection}. Otherwise, it returns an empty string.
	 */
	abstract public String getPackageDisplayName();

	/**
	 * Returns the content of the generated file.
	 */
	abstract public String getNewContent();

	/**
	 * Adds another {@link SpecSection} to this file.
	 */
	abstract public void add(SpecSection specElem);

	/**
	 * Returns all {@link SpecSection}s which were added previously.
	 */
	abstract public Collection<? extends SpecSection> getSpecSections();

	/**
	 * Returns the start of the offset of the given {@link SpecSection} in the generated file.
	 */
	abstract public int getOffsetStart(SpecSection entry);

	/**
	 * Returns the end of the offset of the given {@link SpecSection} in the generated file.
	 */
	abstract public int getOffsetEnd(SpecSection entry);

	/**
	 * Returns the copyright header.
	 */
	public String getCopyrightHeader() {
		return CopyrightHeader.getAdoc();
	}

	@Override
	public int hashCode() {
		return getFile() != null ? getFile().hashCode() : 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SpecFile) {
			SpecFile scf = (SpecFile) obj;
			if (getFile() != null) {
				return getFile().equals(scf.getFile());
			}
			return getFile() == scf.getFile();
		}
		return false;
	}

	/**
	 * Defines the order in the pull-down menu of the changes page in the wizard.
	 */
	@Override
	public int compareTo(SpecFile o) {
		if (o == null) {
			return 1;
		}
		if (getFile() == null) {
			return o.getFile() == null ? 0 : -1;
		}
		String myName = getFile().getName() + getFileKey();
		String otherName = o.getFile().getName() + o.getFileKey();
		return myName.compareTo(otherName);
	}

	@Override
	public String toString() {
		return getFileKey();
	}

}
