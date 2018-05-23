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
 * Generates xpect report with GItHub formatting.
 */
public class GenerateGitHubIssueXpectReportCommandHandler extends GenerateXpectReportCommandHandler {

	@Override
	protected void generateAndDisplayReport(String name, String contents) {
		XpectBugReportUtil.displayGeneratedGitHubIssueConsole(name, contents);
	}

}
