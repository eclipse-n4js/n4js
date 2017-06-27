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

/**
 * {@link IndexEntry}s are extended {@link SourceEntry}s.
 */
public class IndexEntry extends SourceEntry implements Comparable<IndexEntry> {
	final int offsetStart;
	final int offsetEnd;
	@Deprecated
	final String compareString;

	/**
	 * Constructor
	 */
	protected IndexEntry(
			String repository,
			String path,
			String project,
			String folder,
			String trueFolder,
			String module,
			String extension,
			String element,
			String delimiter,
			String property,
			String packageName,
			String moduleName,
			int sourceLine,
			int modulePackageCount,
			String[] fileNames,
			String fileName,
			int offsetStart,
			int offsetEnd) {

		super(
				repository,
				path,
				project,
				folder,
				trueFolder,
				module,
				extension,
				element,
				delimiter,
				property,
				packageName,
				moduleName,
				sourceLine,
				modulePackageCount,
				fileNames,
				fileName);

		this.offsetStart = offsetStart;
		this.offsetEnd = offsetEnd;
		this.compareString = getCompareString();
	}

	IndexEntry(SourceEntry pc, int offsetStart, int offsetEnd) {
		super(pc);
		this.offsetStart = offsetStart;
		this.offsetEnd = offsetEnd;
		this.compareString = getCompareString();
	}

	private String getCompareString() {
		String cs = toPQN();
		cs += "::" + sourceLine;
		cs += "::" + offsetStart;
		cs += "::" + offsetEnd;
		return cs;
	}

	@Override
	public int compareTo(IndexEntry ie) {
		// specKeys are used here for comparison, because they are also
		// used in SpecChangeEntry#compareTo. Since they are equal,
		// both the index file and the adoc file use the same order of
		// module elements.
		return toPQN().compareTo(ie.toPQN());
	}

}
