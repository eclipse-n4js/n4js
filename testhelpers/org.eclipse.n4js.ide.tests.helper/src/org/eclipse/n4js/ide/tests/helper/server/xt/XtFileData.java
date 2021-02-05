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

import org.eclipse.lsp4j.Range;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Workspace;
import org.eclipse.n4js.utils.Strings;
import org.junit.runner.Description;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

/**
 *
 */
public class XtFileData {

	static public class MethodData implements Serializable, Comparable<MethodData> {
		static final public char OPEN_BRACKET = '\u3014';
		static final public char CLOSE_BRACKET = '\u3015';

		final public String comment;
		final public String name;
		final public String[] args;
		final public int count;
		final public String expectation;
		final public int offset;

		public MethodData(String name) {
			this("", name, new String[0], 0, "", 0);
		}

		public MethodData(String comment, String name, String[] args, int count, String expectation, int offset) {
			this.comment = comment.trim();
			this.name = name;
			this.args = args;
			this.count = count;
			this.expectation = expectation.trim();
			this.offset = offset;
		}

		public Description getDescription(XtFileData xtFileData) {
			String descrName = name + "~" + count;
			descrName += ": " + (comment.isBlank() ? getArgs() : comment);
			descrName += " " + OPEN_BRACKET + xtFileData.workspacePath + CLOSE_BRACKET;
			return Description.createTestDescription(getMethodNameWithArgs(), descrName, this);
		}

		/** @return all elements separated by space */
		public String getMethodNameWithArgs() {
			return name + " " + getArgs();
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
	}

	static public class Position {
		final public int line;
		final public int column;

		public Position(int line, int column) {
			this.line = line;
			this.column = column;
		}
	}

	final public File xtFile;
	final public FileURI xtFileURI;
	final public String workspacePath;
	final public String content;
	final public String setupRunnerName;
	final public int[] lineLengths;
	final public Workspace workspace;
	final public List<MethodData> startupMethodData;
	final public Collection<MethodData> testMethodData1;
	final public Collection<MethodData> testMethodData2;
	final public List<MethodData> teardownMethodData;

	public XtFileData(File xtFile, String content, String setupRunnerName, Workspace workspace,
			List<MethodData> startupMethodData, Collection<MethodData> testMethodData1,
			Collection<MethodData> testMethodData2, List<MethodData> teardownMethodData) {

		Preconditions.checkState(xtFile.getName().endsWith("." + N4JSGlobals.XT_FILE_EXTENSION));

		this.xtFile = xtFile;
		this.xtFileURI = new FileURI(xtFile);
		this.workspacePath = computeWorkspacePath(xtFile);
		this.content = content;
		this.setupRunnerName = setupRunnerName;
		this.lineLengths = calculateLineLengths(content);
		this.workspace = workspace;
		this.startupMethodData = startupMethodData;
		this.testMethodData1 = testMethodData1;
		this.testMethodData2 = testMethodData2;
		this.teardownMethodData = teardownMethodData;
	}

	static private String computeWorkspacePath(File xtFile) {
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

	public String getModuleName() {
		String moduleName = xtFile.getName();
		moduleName = moduleName.substring(0, moduleName.length() - 1 - N4JSGlobals.XT_FILE_EXTENSION.length());
		return moduleName;
	}

	public Position getPosition(int offset) {
		for (int curOffset = 0, line = 0; line < lineLengths.length && curOffset < offset; //
				curOffset += lineLengths[line], line++) {

			if (curOffset + lineLengths[line] > offset) {
				int column = curOffset + lineLengths[line] - offset;
				return new Position(line, column);
			}
		}

		return new Position(lineLengths.length - 1, lineLengths[lineLengths.length - 1]);
	}

	public Iterable<MethodData> getTestMethodData() {
		return Iterables.concat(testMethodData1, testMethodData2);
	}

	public boolean noTests() {
		return !getTestMethodData().iterator().hasNext();
	}

	/**  */
	public String getText(Range range) {
		int offsetStart = range.getStart().getLine() + range.getStart().getCharacter();
		int offsetEnd = range.getEnd().getLine() + range.getEnd().getCharacter();

		return getText(offsetStart, offsetEnd - offsetStart);
	}

	/**  */
	public String getText(int offset, int length) {
		return content.substring(offset, offset + length);
	}
}
