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
package org.eclipse.n4js.packagejson.xpect.tests;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableSet;

import java.util.Set;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFolder;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtParentRunner;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtSuppressedIssues;
import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.n4js.validation.IssueCodes;
import org.junit.runner.RunWith;

/**
 * Common JUnit runner implementation that uses some annotations of Xpect to enable UI features of the Eclipse IDE
 * regarding JUnit view and context menu entries.
 */
@RunWith(XtParentRunner.class)
// class name needs to end with 'Test' to get picket up by maven
public class PackageJsonXtTest {

	@XtFolder
	static String getFolder() {
		return "xpect-xt";
	}

	@XtSuppressedIssues
	static Set<String> getSuppressedIssueCodes() {
		return unmodifiableSet(newHashSet(
				JSONIssueCodes.JSON_COMMENT_UNSUPPORTED.name(),
				IssueCodes.PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME));
	}
}
