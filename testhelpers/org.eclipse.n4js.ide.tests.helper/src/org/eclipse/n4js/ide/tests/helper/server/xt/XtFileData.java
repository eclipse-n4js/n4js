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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Meta data to describe a test file
 */
public class XtFileData {

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
	final public List<XtMethodData> startupMethodData;
	/** Test methods, first run */
	final public Collection<XtMethodData> testMethodData1;
	/** Test methods, second run */
	final public Collection<XtMethodData> testMethodData2;
	/** Methods to execute to terminate the LSP server */
	final public List<XtMethodData> teardownMethodData;
	/** Modifiers stated in the setup section */
	final public Set<String> configModifiers;

	/** Constructor */
	public XtFileData(File xtFile, String content, String setupRunnerName, XtWorkspace workspace,
			List<XtMethodData> startupMethodData, Collection<XtMethodData> testMethodData1,
			Collection<XtMethodData> testMethodData2, List<XtMethodData> teardownMethodData,
			String[] configModifiers) {

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
		this.configModifiers = Sets.newHashSet(configModifiers);
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
	public Iterable<XtMethodData> getTestMethodData() {
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

	/** @return true iff all issues should be ignored in this xt file */
	public boolean isModifierIgnoreIssues() {
		return configModifiers.contains("IGNORE_ISSUES");
	}
}
