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
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;
import org.junit.runner.Description;

import com.google.common.base.Preconditions;

/**
 *
 */
public class XtFileData {

	static public class MethodData implements Serializable {
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
	final public String workspacePath;
	final public String content;
	final public String setupRunnerName;
	final public int[] lineLengths;
	final public Project project;
	final public List<MethodData> startupMethodData;
	final public List<MethodData> testMethodData;
	final public List<MethodData> teardownMethodData;

	public XtFileData(File xtFile, String content, String setupRunnerName, Project project,
			List<MethodData> startupMethodData, List<MethodData> testMethodData, List<MethodData> teardownMethodData) {

		Preconditions.checkState(xtFile.getName().endsWith("." + N4JSGlobals.XT_FILE_EXTENSION));

		this.xtFile = xtFile;
		this.workspacePath = computeWorkspacePath(xtFile);
		this.content = content;
		this.setupRunnerName = setupRunnerName;
		this.lineLengths = calculateLineLengths(content);
		this.project = project;
		this.startupMethodData = startupMethodData;
		this.testMethodData = testMethodData;
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
}