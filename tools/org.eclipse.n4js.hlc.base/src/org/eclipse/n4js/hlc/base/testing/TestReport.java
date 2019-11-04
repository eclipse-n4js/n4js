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
package org.eclipse.n4js.hlc.base.testing;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Simple test report. Can be easily written to the disk in a junit like test format.
 */
public class TestReport {

	private final StringBuilder reportContent;

	@SuppressWarnings("javadoc")
	public TestReport(StringBuilder content) {
		reportContent = content;
	}

	/** Writes XML content of the report to the given file. */
	public void dump(File toFile) throws IOException {
		Files.asCharSink(toFile, Charsets.UTF_8).write(reportContent);
	}

	/**
	 * Returns report content as String. Discouraged use: for large reports will cause a lot of {@code String}
	 * processing. In production just use {@link #dump} method to write report to a file.
	 */
	public String getReportContent() {
		return reportContent.toString();
	}

}
