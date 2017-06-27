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

import org.eclipse.n4js.jsdoc2spec.SpecFile;

/**
 * The index file contains a tree structure of all elements of the code base. The structure of the tree begins with the
 * repository and continues with the path, project, source folder, module folders, the module name, and ends with the
 * element name. In order to reduce the number of tabs, hence file size, some structure parts are collapsed.
 * <p>
 * Each leave, i.e. each code Element is followed by (1) its source code line in its original file, and the (2)
 * beginning and (3) end line of its generated adoc documentation. These numbers are separated by two colons (::). In
 * case of polyfilled files, the true location of a code element might be in a different source folder. If so, the true
 * source folder is specified in an additional postfix (again following on a double colon).
 * <p>
 * Refer to the PQN (Partial Qualified Name) section of the AsciiSpec project documentation for more information.
 */
public class IndexFileWriter extends IndexEntryWriter {

	@Override
	protected String getFileName() {
		return FileSystem.INDEX_FILE_NAME;
	}

	@Override
	protected SpecFile createSpecFile(File file, String content) {
		return new SpecIndexFile(file, content);
	}

	@Override
	protected void appendEntry(IndexEntry ie) {
		boolean superDirChanged = false;
		for (int i = 0; i < ie.adocPathElems.length; i++) {
			if (superDirChanged || !isEqualFileName(ie, lastIE, i)) {
				superDirChanged = true;

				for (int j = 0; j < i; j++) {
					strb.append("\t");
				}
				String pathElem = setExtensionOnLastPathElem(ie, i);
				strb.append(pathElem);
				strb.append("\n");
			}
		}

		appendElement(ie);
	}

	private String setExtensionOnLastPathElem(IndexEntry ie, int i) {
		String pathElem = ie.adocPathElems[i];
		if (ie.adocPathElems.length - 1 == i)
			pathElem = pathElem.replace(".adoc", ie.extension);
		return pathElem;
	}

	private boolean isEqualFileName(IndexEntry ie1, IndexEntry ie2, int i) {
		if (ie1 == null || ie2 == null)
			return false;
		if (ie1.adocPathElems.length <= i || ie2.adocPathElems.length <= i)
			return false;

		return setExtensionOnLastPathElem(ie1, i).equals(setExtensionOnLastPathElem(ie2, i));
	}

	private void appendElement(IndexEntry ie) {
		strb.append("\t\t\t\t");
		strb.append(ie.element);
		strb.append(ie.delimiter);
		strb.append(ie.property);
		strb.append("::");
		strb.append(ie.sourceLine);
		strb.append("::");
		strb.append(ie.offsetStart);
		strb.append("::");
		strb.append(ie.offsetEnd);

		if (!ie.folder.equals(ie.trueFolder)) {
			strb.append("::");
			strb.append(ie.repository);
			strb.append(":");
			strb.append(ie.path.replace("/", "."));
			strb.append(":");
			strb.append(ie.project);
			strb.append(":");
			strb.append(ie.trueFolder.replace("/", "."));
		}

		strb.append(FileSystem.NL);
	}

}
