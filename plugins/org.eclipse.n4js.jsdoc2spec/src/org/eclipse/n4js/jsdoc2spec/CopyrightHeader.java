/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc2spec;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 */
public class CopyrightHeader {
	static final String DEFAULT_CRH_FILE_NAME = "copyrightheader.adoc";

	/** The copyright header which is prepended to every generated file. */
	static String COPYRIGHT_HEADER = "";

	/** Sets the current copyright header. */
	public static void set(String header) {
		if (header == null)
			header = "";
		COPYRIGHT_HEADER = header;
	}

	/** Returns true iff there is no copyright header. */
	public static boolean isEmpty() {
		return COPYRIGHT_HEADER == null || COPYRIGHT_HEADER.isEmpty();
	}

	/** Returns the copyright header in adoc syntax. */
	public static String getAdoc() {
		if (isEmpty())
			return "";
		return "////\n" + COPYRIGHT_HEADER + "\n////\n\n";
	}

	/** Returns the copyright header in idx syntax. */
	public static String getIdx() {
		if (isEmpty())
			return "";
		return getCrhForIdxFiles();
	}

	private static String getCrhForIdxFiles() {
		String newCRH = "#\n# " + String.join("\n# ", COPYRIGHT_HEADER.split("\n")) + "\n#\n\n";
		newCRH = newCRH.replaceAll(" \n", "\n");
		return newCRH;
	}

	/**
	 * Reads the default copyright header file at the given location. In case the default file is missing, an empty
	 * string is returned.
	 */
	public static String readDefault(Path path) {
		Path defaultCRH = path.resolve(DEFAULT_CRH_FILE_NAME);
		String crh = "";
		try {
			List<String> lines = Files.readAllLines(defaultCRH, StandardCharsets.UTF_8);
			crh = String.join("\n", lines);
		} catch (Throwable t) {
			// ignore
		}
		return crh.trim();
	}

}
