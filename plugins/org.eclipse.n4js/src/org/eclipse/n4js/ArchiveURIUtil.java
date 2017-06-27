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
package org.eclipse.n4js;

import java.util.List;
import java.util.zip.ZipEntry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.Strings;

/**
 * Utilities when dealing with archive URIs.
 */
public class ArchiveURIUtil {

	/**
	 * Creates a new archive URI that points to the given archive entry.
	 *
	 * @param archiveFile
	 *            the uri to the archive
	 * @param entry
	 *            the entry
	 * @return a URI that points to the entry
	 *
	 * @see URI#createHierarchicalURI(String, String, String, String[], String, String)
	 */
	public static URI createURI(URI archiveFile, ZipEntry entry) {
		String entryName = entry.getName();
		List<String> segmentList = Strings.split(entryName, '/');
		String[] segments = segmentList.toArray(new String[segmentList.size()]);
		URI result = URI.createHierarchicalURI(
				"archive",
				archiveFile.toString() + "!",
				null,
				segments,
				null,
				null);
		return result;
	}

	/**
	 * Creates a new archive URI that points to the given archive entry. The entry has to be a simple name, e.g. it may
	 * not contain any invalid segment characters.
	 *
	 * @param archiveFile
	 *            the uri to the archive
	 * @param entry
	 *            the name of the entry
	 * @return a URI that points to the entry
	 *
	 * @see URI#createHierarchicalURI(String, String, String, String[], String, String)
	 */
	public static URI createURI(URI archiveFile, String entry) throws IllegalArgumentException {
		URI result = URI.createHierarchicalURI(
				"archive",
				archiveFile.toString() + "!",
				null,
				new String[] { entry },
				null,
				null);
		return result;
	}

}
