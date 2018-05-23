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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.jsdoc2spec.CopyrightHeader;
import org.eclipse.n4js.jsdoc2spec.SpecFile;

class IndicesCreator {

	/**
	 * Creates all index files of a given {@code specChangeSet}. Each index file refers to one specific repository. The
	 * files entries are ordered alphabetically and hierarchically.
	 *
	 * @throws UnsupportedEncodingException
	 *             thrown when the location cannot be transformed to a URL
	 */
	static List<SpecFile> createIndexFiles(File rootDir, Collection<SpecFile> specChangeSet)
			throws UnsupportedEncodingException {

		String crh = CopyrightHeader.readDefault(rootDir.toPath());
		CopyrightHeader.set(crh);

		ArrayList<IndexEntry> entries = getIndexEntries(specChangeSet);
		Collections.sort(entries);
		List<SpecFile> indexFiles = new ArrayList<>();

		IndexFileWriter iFW = new IndexFileWriter();
		iFW.serialize(entries);
		indexFiles.add(iFW.getSpecFile(rootDir));

		PDFContentFileWriter pdfFW = new PDFContentFileWriter();
		pdfFW.serialize(entries);
		indexFiles.add(pdfFW.getSpecFile(rootDir));

		WebContentFileWriter webFW = new WebContentFileWriter();
		webFW.serialize(entries);
		indexFiles.addAll(webFW.getSpecFiles(rootDir));

		return indexFiles;
	}

	/**
	 * This method creates a list of {@link IndexEntry}s from a list of {@link SpecModuleFile}s. Since the given
	 * collection <code>specFiles</code> also contains other {@link SpecFile}s, it is filtered first. It retrieves start
	 * and end offsets and also a {@link SourceEntry}.
	 */
	static private ArrayList<IndexEntry> getIndexEntries(Collection<SpecFile> specFiles) {
		ArrayList<IndexEntry> entries = new ArrayList<>();
		for (SpecFile specFile : specFiles) {
			if (specFile instanceof SpecModuleFile) {
				SpecModuleFile smf = (SpecModuleFile) specFile;

				for (SpecIdentifiableElementSection specIE : smf.getSpecSections()) {
					SourceEntry pc = specIE.getSourceEntry();
					int offsetStart = specFile.getOffsetStart(specIE);
					int offsetEnd = specFile.getOffsetEnd(specIE);

					if (pc == null) {
						pc = specIE.getSourceEntry();
					}

					IndexEntry ie = new IndexEntry(pc, offsetStart, offsetEnd);
					entries.add(ie);
				}
			}
		}
		return entries;
	}

}
