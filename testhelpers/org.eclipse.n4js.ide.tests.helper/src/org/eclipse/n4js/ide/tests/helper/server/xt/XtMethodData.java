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

import java.io.Serializable;

import org.junit.runner.Description;

import com.google.common.base.Objects;

/**
 * Meta data to describe a test method
 */
public class XtMethodData implements Serializable, Comparable<XtMethodData> {
	/** Opening bracket used for the Xpect Eclipse Plugin to enable 'Open Xpect Method' in context menu */
	static final public char OPEN_BRACKET = '\u3014';
	/** Closing bracket used for the Xpect Eclipse Plugin to enable 'Open Xpect Method' in context menu */
	static final public char CLOSE_BRACKET = '\u3015';

	/** Name of the xt file this method is contained in. */
	final public String fileName;
	/** Test comment. Stated before the test keyword. */
	final public String comment;
	/** Test name. Stated after the test keyword. */
	final public String name;
	/** Test arguments. Stated after the test name. */
	final public String args;
	/** Test number. Tests with same names are grouped. */
	final public int count;
	/** Test expectation. Stated after the test divider ({@code -->} or {@code ---}). */
	final public String expectation;
	/** End offset of test location in file */
	final public int offset;
	/** True iff this test is expected to fail */
	final public boolean isFixme;
	/** True if this test should be ignored */
	final public boolean isIgnore;

	/** Constructor */
	public XtMethodData(String name) {
		this("", "", name, "", 0, "", 0, false, false);
	}

	/** Constructor */
	public XtMethodData(String fileName, String comment, String name, String args, int count, String expectation,
			int offset,
			boolean isFixme, boolean isIgnore) {

		this.fileName = fileName;
		this.comment = comment.trim();
		this.name = name;
		this.args = args;
		this.count = count;
		this.expectation = expectation.trim();
		this.offset = offset;
		this.isFixme = isFixme;
		this.isIgnore = isIgnore;
	}

	/** @return Description for JUnit */
	public Description getDescription(XtFileData xtFileData) {
		String xpectMethodName = name + "~" + count;
		String commentOrArgs = (comment.isBlank() ? args : comment);
		String delimiter = !getModifier().isBlank() && !commentOrArgs.isBlank() ? " " : "";
		String modifierWithCommentOrArgs = getModifier() + delimiter + commentOrArgs;
		String xtFileLocation = OPEN_BRACKET + xtFileData.relativePath + CLOSE_BRACKET;

		String className = (comment.isBlank() ? xpectMethodName : comment);

		// for compatibility with the Xpect ui plug-in
		// the description name needs to start with the method name, the '~', the count
		// followed by the file location in special brackets
		// see:
		// https://github.com/eclipse/Xpect/blob/0e5186a5fd96d4c82536fcff9acfa6ffada9fff8/org.eclipse.xpect.ui.junit/src/org/eclipse/xpect/ui/junit/TestDataUIUtil.java#L82
		String descrName = xpectMethodName + ": " + modifierWithCommentOrArgs + " " + xtFileLocation;
		return Description.createTestDescription(className, descrName, this);
	}

	/** @return all elements separated by space */
	public String getMethodNameWithArgs() {
		return name + (hasArgs() ? " " + args : "");
	}

	/** @return true iff {@link #args} returns a non-blank string */
	public boolean hasArgs() {
		return !args.isBlank();
	}

	/** @return the modifier's keyword or null if not existing */
	public String getModifier() {
		if (isFixme) {
			return XtFileDataParser.XtMethodIterator.XT_FIXME.trim();
		}
		if (isIgnore) {
			return XtFileDataParser.XtMethodIterator.XT_IGNORE.trim();
		}
		return "";
	}

	@Override
	public int compareTo(XtMethodData md) {
		int nameCompareResult = fileName.compareTo(md.fileName);
		if (nameCompareResult != 0) {
			return nameCompareResult;
		}
		return offset - md.offset;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XtMethodData) {
			XtMethodData md = (XtMethodData) obj;
			return Objects.equal(md.fileName, fileName) && md.offset == offset;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return fileName.hashCode() + offset;
	}

	@Override
	public String toString() {
		return name + " --> " + expectation;
	}
}