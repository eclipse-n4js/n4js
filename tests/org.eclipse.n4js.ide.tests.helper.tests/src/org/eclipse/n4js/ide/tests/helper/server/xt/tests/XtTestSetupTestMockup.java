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
package org.eclipse.n4js.ide.tests.helper.server.xt.tests;

import java.util.Set;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFolder;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtSuppressedIssues;

/**
 * JUnit runner for testing the xt framework.
 */
public class XtTestSetupTestMockup {
	static String folder;
	static Set<String> suppressedIssues;

	@XtFolder
	static String getFolder() {
		return folder;
	}

	@XtSuppressedIssues
	static Set<String> getSuppressedIssues() {
		return suppressedIssues;
	}
}
