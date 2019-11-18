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
package org.eclipse.n4js.filechecker;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Report compiled during checking a file
 */
public class Report implements Comparable<Report> {
	/** File path */
	public final Path path;
	/** Detected problems */
	public final List<String> problems = new ArrayList<>();
	private Throwable error;
	private boolean hasCopyrightHeader = false;
	private boolean isIgnored = false;
	private boolean isThirdParty = false;

	/** Constructor */
	public Report(Path path) {
		this.path = path;
	}

	/** Sets this report to be ignored */
	public void setToIgnored() {
		this.isIgnored = true;
	}

	/** Sets this report to be a third-party file */
	public void setToThirdParty() {
		this.isThirdParty = true;
	}

	/** Sets this report to be missing a copyright header */
	public void setToHasCRH() {
		this.hasCopyrightHeader = true;
	}

	/** Sets this report to have an error */
	public void setThrowable(Throwable error) {
		this.error = error;
	}

	/** Returns true iff the file is in a test project */
	public boolean isInTestProject() {
		String testProjectSuffix = "tests" + File.separator;
		boolean isInTestProject = path.toString().contains(testProjectSuffix);
		boolean isJavaTestFile = path.toString().endsWith("Test.java");
		boolean isInHlcTestDir = path.toString().contains("org.eclipse.n4js.hlc/src/test");
		return isInTestProject || isJavaTestFile || isInHlcTestDir;
	}

	/** Returns true iff the file is not in a test project */
	public boolean isNotInTestProject() {
		return !isInTestProject();
	}

	/** Returns true iff the file is located in the N4JS repository */
	public boolean isInN4JSRepo() {
		boolean containsRepoDir = path.toString().contains(File.separator + "n4js" + File.separator);
		return containsRepoDir;
	}

	/** Returns true iff the file is located in the n4js-extended repository */
	public boolean isInN4JSExtendedRepo() {
		boolean containsRepoDir = path.toString().contains(File.separator + "n4js-extended" + File.separator);
		return containsRepoDir;
	}

	/** Returns true iff this report should be ignored */
	public boolean isIgnored() {
		return isIgnored;
	}

	/** Returns true iff this report should be ignored */
	public boolean isNotIgnored() {
		return !isIgnored();
	}

	/** Returns true iff this report is from a third-party file */
	public boolean isThirdParty() {
		return isThirdParty;
	}

	/** Returns true iff this report is erroneous */
	public boolean isErroneous() {
		return error != null;
	}

	/** Returns true iff this report relates to a file that has no copyright header */
	public boolean isMissingCopyrightHeader() {
		return path.toFile().isFile() && !isHavingCopyrightHeader();
	}

	/** Returns true iff this report relates to a file that has a copyright header */
	public boolean isHavingCopyrightHeader() {
		return path.toFile().isFile() && (hasCopyrightHeader || isThirdParty());
	}

	/** Returns true iff this report is not ignored and has no {@link FileChecker} problems */
	public boolean isValid() {
		return !isIgnored() && problems.isEmpty();
	}

	/** Returns true iff this report is not ignored and has {@link FileChecker} problems */
	public boolean isInvalid() {
		return !isIgnored() && !problems.isEmpty();
	}

	/** Returns the file extension. In case there is none, returns the file name. */
	public String getFileExtension() {
		String pathStr = path.toString();
		int idxDot = pathStr.lastIndexOf(".") + 1;
		int idxFileSep = pathStr.lastIndexOf(File.separator) + 1;
		String extension = pathStr.substring(Math.max(idxDot, idxFileSep));
		return extension;
	}

	/** Returns the error */
	public Throwable getError() {
		return error;
	}

	@Override
	public int compareTo(Report report) {
		return path.compareTo(report.path);
	}

}
