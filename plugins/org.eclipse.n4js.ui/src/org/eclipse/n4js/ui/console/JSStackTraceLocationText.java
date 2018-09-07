/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.console;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Composite of java script stack trace informations, i.e. file name, line and column. It also provides the pattern to
 * extract this information from a string.
 *
 * Note that at the moment only file based locations are supported.
 */
public class JSStackTraceLocationText {

	/**
	 * Pattern used to extract the stack trace informatoin from a string.
	 */
	public final static Pattern JAVASCRIPT_STRACKTRACE_LOCATION_PATTERN = Pattern
			.compile("\\((.*):([0-9]+):([0-9]+)\\)");

	/**
	 * File name as extracted from the stack trace string. When node.js creates the stack trace, this is the absolute
	 * file name.
	 */
	public final String fileName;

	/**
	 * Line number of the stack trace as provided by the stack trace string starting at 1.
	 */
	public final int line;

	/**
	 * Column number of the stack trace as provided by the stack trace string starting at 1.
	 */
	public final int column;

	/**
	 * Creates a StackTrace location of the first occurrence in the given text.
	 *
	 * @param linkText
	 *            E.g., "(/absolute/path.js:line:col)"
	 * @throws IllegalArgumentException
	 *             if no more matches are found
	 */
	public JSStackTraceLocationText(String linkText) {
		Matcher matcher = JAVASCRIPT_STRACKTRACE_LOCATION_PATTERN.matcher(linkText);
		if (matcher.find()) {
			String name = matcher.group(1);
			if (name.startsWith("file://")) {
				name = name.substring("file://".length());
			}
			fileName = name;
			line = Integer.parseInt(matcher.group(2)); // 1 based, we do not change it here
			column = Integer.parseInt(matcher.group(3));
		} else {
			throw new IllegalArgumentException("No matches found");
		}
	}

	/**
	 * Creates a new StackTrace location with the next match. This is used when multiple locations are created.
	 */
	public JSStackTraceLocationText(Matcher matcher) {
		String name = matcher.group(1);
		if (name.startsWith("file://")) {
			name = name.substring("file://".length());
		}
		fileName = name;
		line = Integer.parseInt(matcher.group(2)); // 1 based, we do not change it here
		column = Integer.parseInt(matcher.group(3));
	}

	/**
	 * Creates a new location with explicit data.
	 */
	public JSStackTraceLocationText(Path path, int line, int column) {
		this.fileName = path.toString();
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns the simple file name.
	 */
	public String getSimpleName() {
		File f = new File(fileName);
		return f.getName();
	}

	/**
	 * Returns the file extension or empty string, if no extension is found.
	 */
	public String getExtension() {
		String name = getSimpleName();
		int i = name.lastIndexOf('.');
		if (i >= 0 && i < name.length()) {
			return name.substring(i + 1);
		}
		return "";
	}

}