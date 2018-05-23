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
package org.eclipse.n4js.xpect.ui.results;

/**
 * Utility that generates bug contents.
 */
public class XpectBugReportUtil {

	/**
	 * Convenience method, delegates to {@link #displayGeneratedGitHubIssueConsole(String, String)}
	 */
	public static void displayGeneratedBugConsole(String suiteName, String contents) {

		// TODO
		displayGeneratedGitHubIssueConsole(suiteName, contents);
	}

	/**
	 * Write to console contents of generated bug report (with JIRA formatting). Creates (or finds by name) special
	 * {@link org.eclipse.ui.console.MessageConsole} instance where data is written.
	 *
	 * @param suiteName
	 *            name used in report and as console name
	 * @param contents
	 *            contents used in report
	 */
	public static void displayGeneratedJiraBugConsole(String suiteName, String contents) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"{panel:title=Generated bug report|borderStyle=dashed|borderColor=#ccc|titleBGColor=#8192A6|bgColor=#dedee0}\n");
		sb.append("{{").append(suiteName).append("}}\n");
		sb.append("{code}\n").append(contents).append("\n{code}\n");
		sb.append("{panel}\n");

		XpectConsole console = ConsoleDisplayMgr.getOrCreate("generated bug for "
				+ suiteName);
		console.clear();
		console.log(sb.toString());
	}

	/**
	 * Write to console contents of generated bug report (with GitHub formatting). Creates (or finds by name) special
	 * {@link org.eclipse.ui.console.MessageConsole} instance where data is written.
	 *
	 * @param suiteName
	 *            name used in report and as console name
	 * @param contents
	 *            contents used in report
	 */
	public static void displayGeneratedGitHubIssueConsole(String suiteName, String contents) {

		StringBuilder sb = new StringBuilder();
		sb.append("#### Generated bug report\n");
		sb.append("##### ").append(suiteName).append("\n");
		sb.append("```\n").append(contents).append("\n```\n");

		XpectConsole console = ConsoleDisplayMgr.getOrCreate("generated bug for "
				+ suiteName);
		console.clear();
		console.log(sb.toString());
	}

}
