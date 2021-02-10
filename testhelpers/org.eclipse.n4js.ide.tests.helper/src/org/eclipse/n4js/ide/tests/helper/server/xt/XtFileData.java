/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.Strings;
import org.junit.runner.Description;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

/**
 * Meta data to describe a test file
 */
public class XtFileData {

	/**
	 * Meta data to describe a test method
	 */
	static public class MethodData implements Serializable, Comparable<MethodData> {
		/** Opening bracket used for the Xpect Eclipse Plugin to enable 'Open Xpect Method' in context menu */
		static final public char OPEN_BRACKET = '\u3014';
		/** Closing bracket used for the Xpect Eclipse Plugin to enable 'Open Xpect Method' in context menu */
		static final public char CLOSE_BRACKET = '\u3015';

		/** Test comment. Stated before the test keyword. */
		final public String comment;
		/** Test name. Stated after the test keyword. */
		final public String name;
		/** Test arguments. Stated after the test name. */
		final public String[] args;
		/** Test number. Tests with same names are grouped. */
		final public int count;
		/** Test expectation. Stated after the test divider ({@code -->} or {@code ---}). */
		final public String expectation;
		/** End offset of test location in file */
		final public int offset;

		/** Constructor */
		public MethodData(String name) {
			this("", name, new String[0], 0, "", 0);
		}

		/** Constructor */
		public MethodData(String comment, String name, String[] args, int count, String expectation, int offset) {
			this.comment = comment.trim();
			this.name = name;
			this.args = args;
			this.count = count;
			this.expectation = expectation.trim();
			this.offset = offset;
		}

		/** @return Description for JUnit */
		public Description getDescription(XtFileData xtFileData) {
			String className = (comment.isBlank() ? getArgs() : comment);
			String descrName = name + "~" + count;
			descrName += ": " + (comment.isBlank() ? getArgs() : comment);
			descrName += " " + OPEN_BRACKET + xtFileData.relativePath + CLOSE_BRACKET;
			return Description.createTestDescription(className, descrName, this);
		}

		/** @return all elements separated by space */
		public String getMethodNameWithArgs() {
			return name + (hasArgs() ? " " + getArgs() : "");
		}

		/** @return true iff {@link #getArgs()} returns a non-blank string */
		public boolean hasArgs() {
			return !getArgs().isBlank();
		}

		/** @return all args separated by space */
		public String getArgs() {
			return Strings.join(" ", (Object[]) args);
		}

		@Override
		public int compareTo(MethodData md) {
			return offset - md.offset;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MethodData) {
				MethodData md = (MethodData) obj;
				return md.offset == offset;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return offset;
		}

		@Override
		public String toString() {
			return name + " --> " + expectation;
		}
	}

	/** Test file */
	final public File xtFile;
	/** Test file as {@link #xtFileURI} */
	final public FileURI xtFileURI;
	/** Relative path of the test file inside the test bundle */
	final public String relativePath;
	/** Content of test file */
	final public String content;
	/** Name of the JUnit runner */
	final public String setupRunnerName;
	/** Array of lengths per line */
	final public int[] lineLengths;
	/** Workspace, either default or according to description in SETUP section */
	final public XtWorkspace workspace;
	/** Methods to execute to start the LSP server */
	final public List<MethodData> startupMethodData;
	/** Test methods, first run */
	final public Collection<MethodData> testMethodData1;
	/** Test methods, second run */
	final public Collection<MethodData> testMethodData2;
	/** Methods to execute to terminate the LSP server */
	final public List<MethodData> teardownMethodData;

	/** Constructor */
	public XtFileData(File xtFile, String content, String setupRunnerName, XtWorkspace workspace,
			List<MethodData> startupMethodData, Collection<MethodData> testMethodData1,
			Collection<MethodData> testMethodData2, List<MethodData> teardownMethodData) {

		Preconditions.checkState(xtFile.getName().endsWith("." + N4JSGlobals.XT_FILE_EXTENSION));

		this.xtFile = xtFile;
		this.xtFileURI = new FileURI(xtFile);
		this.relativePath = computeRelativePath(xtFile);
		this.content = content;
		this.setupRunnerName = setupRunnerName;
		this.lineLengths = calculateLineLengths(content);
		this.workspace = workspace;
		this.startupMethodData = startupMethodData;
		this.testMethodData1 = testMethodData1;
		this.testMethodData2 = testMethodData2;
		this.teardownMethodData = teardownMethodData;
	}

	static private String computeRelativePath(File xtFile) {
		Path currentDir = new File("").getAbsoluteFile().toPath();
		Path relXtFile = currentDir.relativize(xtFile.toPath());

		return relXtFile.toString();
	}

	static private int[] calculateLineLengths(String content) {
		List<Integer> lengths = new ArrayList<>();
		for (int idx = content.indexOf("\n"), lstIdx = 0; idx > -1; //
				lstIdx = idx, idx = content.indexOf("\n", idx + 1)) {

			lengths.add(idx - lstIdx);
		}

		int[] lineLengths = new int[lengths.size()];
		for (int i = 0; i < lengths.size(); i++) {
			lineLengths[i] = lengths.get(i);
		}
		return lineLengths;
	}

	/** @returns a file without the {@code .xt} extension */
	static public File stripXtExtension(File xtFile) {
		String nameWithXt = xtFile.getName();
		Preconditions.checkArgument(nameWithXt.endsWith("." + N4JSGlobals.XT_FILE_EXTENSION));
		String name = nameWithXt.substring(0, nameWithXt.length() - 1 - N4JSGlobals.XT_FILE_EXTENSION.length());
		return new File(xtFile.getParentFile(), name);
	}

	/** @return the position computed from the given offset */
	public Position getPosition(int offset) {
		for (int curOffset = 0, line = 0; line < lineLengths.length && curOffset < offset; //
				curOffset += lineLengths[line], line++) {

			if (curOffset + lineLengths[line] > offset) {
				int character = curOffset + lineLengths[line] - offset;
				return new Position(line, character);
			}
		}

		return new Position(lineLengths.length - 1, lineLengths[lineLengths.length - 1]);
	}

	/** @return the offset computed from the given #{@link Position} */
	public int getOffset(org.eclipse.lsp4j.Position pos) {
		// 1-based character
		return getOffset(pos.getLine(), pos.getCharacter() + 1);
	}

	/** @return the offset computed from the given line and character */
	public int getOffset(int line, int character) {
		int offset = 0;
		for (int i = 0; i < line; i++) {
			offset += lineLengths[i];
		}
		return offset + character;
	}

	/** @return all tests */
	public Iterable<MethodData> getTestMethodData() {
		return Iterables.concat(testMethodData1, testMethodData2);
	}

	/** @return true iff the file defines no tests */
	public boolean noTests() {
		return !getTestMethodData().iterator().hasNext();
	}

	/** @return substring of this file's content in the given range */
	public String getText(Range range) {
		int offsetStart = getOffset(range.getStart());
		int offsetEnd = getOffset(range.getEnd());

		return getText(offsetStart, offsetEnd - offsetStart);
	}

	/** @return substring of this file's content at the given offset and with the given length */
	public String getText(int offset, int length) {
		return content.substring(offset, offset + length);
	}

}
