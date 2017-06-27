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
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import org.eclipse.n4js.jsdoc2spec.SpecFile;

/**
 * This class is used as a base class to create the index.idx and the pdfBook.adoc file, which is later used to compile
 * the pdfBook.pdf file. As input, this class takes a list of all {@link IndexEntry}s. The result is a file.
 */
abstract class IndexEntryWriter {
	protected IndexEntry lastIE;
	protected StringBuilder strb;

	/**
	 * This method is invoked for every {@link IndexEntry} in a code base. When called, it can be serialized and written
	 * to {@link #strb}.
	 */
	abstract protected void appendEntry(IndexEntry ie);

	/**
	 * @return the contents file name
	 */
	abstract protected String getFileName();

	/**
	 * @return the contents file name
	 */
	abstract protected SpecFile createSpecFile(File file, String content);

	protected void clear() {
		lastIE = null;
		strb = null;
	}

	protected void startNewFile() {
		strb = new StringBuilder();
	}

	protected void append(IndexEntry ie) {
		Objects.requireNonNull(strb);
		appendEntry(ie);
		lastIE = ie;
	}

	/**
	 * @return true iff the file will have contents.
	 */
	public boolean hasContent() {
		return lastIE != null && strb != null;
	}

	/**
	 * Takes all entries of a code base. This method indirectly invokes the abstract method
	 * {@link #appendEntry(IndexEntry)}.
	 */
	public void serialize(ArrayList<IndexEntry> entries) {
		clear();
		startNewFile();
		for (IndexEntry ie : entries) {
			append(ie);
		}
	}

	/**
	 * This method should be invoked after all entries were passed to this instance using {@link #serialize(ArrayList)}.
	 * It returns a file including all contents ready to be written to the disk.
	 */
	public SpecFile getSpecFile(File rootDir) {
		String fileName = getFileName();
		String absFileName = rootDir + File.separator + FileSystem.DIR_ADOC_GEN + File.separator + fileName;
		File indexFile = new File(absFileName);
		String indexContent = strb.toString();
		SpecFile scf = createSpecFile(indexFile, indexContent);
		return scf;
	}
}
