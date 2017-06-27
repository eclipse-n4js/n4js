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
package org.eclipse.n4js.xpect.ui.runner;

import org.junit.runner.Description;

/**
 * Utility functions to extract different name parts of xpect file {@link Description}.
 *
 * <pre>
 * Assumes two formats of {@link Description#getDisplayName()} :
 * <ul>
 *  <li> suite format : tst.n4js.xt://C:/Users/Administrator/Workspace/TestProject/src
 *  <li> crashed suite format : t034.n4js.xt: /Users/Administrator/Workspace/TestProject/src(org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestClass)
 *  <li> test format : //C:/Users/Administrator/Workspace/TestProject/src/tst.n4js.xt#tst~0(org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestClass)
 *  <li> crashed test format : initializationError(org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestClass)
 * </ul>
 * </pre>
 */
// TODO refactor : remove duplicated code
public class N4IDEXpectFileNameUtil {

	/** marker for test initialization error */
	final public static String TEST_FILE_INIT_ERROR_MSG = "initializationError";

	/***/
	public static String getSuiteName(Description description) {
		String text = null;
		if (description.isSuite()) {
			String s = description.getDisplayName();
			int posSemi = s.indexOf(":");
			text = s.substring(0, posSemi);
		}
		return text;
	}

	/***/
	public static String getTestName(Description description) {
		String text = null;
		if (description.isTest()) {
			String s = description.getDisplayName();

			if (s.startsWith(TEST_FILE_INIT_ERROR_MSG)) {
				return TEST_FILE_INIT_ERROR_MSG;
			}

			// seems like malformed xt file - no XPECT comment ?
			if (s.indexOf("#") < 0 || s.indexOf("~") < 0) {
				return s;
			}

			int posXT = s.indexOf("#");
			int posTM = s.indexOf("~", posXT);
			text = s.substring(posXT + 1, posTM);
		}
		return text;
	}

	/***/
	public static String getFileName(Description description) {
		String text = null;
		if (description.isSuite()) {
			String s = description.getDisplayName();
			int posSemi = s.indexOf(":");
			text = s.substring(0, posSemi);
		} else if (description.isTest()) {
			String s = description.getDisplayName();
			int posXT = s.indexOf("#");
			int posSL = s.lastIndexOf("/");
			text = s.substring(posSL + 1, posXT);
		}
		return text;
	}

	/***/
	public static String getFilePath(Description description) {
		String text = null;
		if (description.isSuite()) {
			String location = "";
			String s = description.getDisplayName();
			int posSL = s.indexOf("/");
			/* when xpect test run crashed there is different display name */
			int posBr = s.indexOf("(");
			if (posBr == -1) {
				location = s.substring(posSL);
			} else {
				location = s.substring(posSL, posBr);
			}

			int posSemi = s.indexOf(":");
			String fileName = s.substring(0, posSemi);

			text = location + "/" + fileName;
		} else if (description.isTest()) {
			String s = description.getDisplayName();
			if (s.startsWith(TEST_FILE_INIT_ERROR_MSG)) {
				return TEST_FILE_INIT_ERROR_MSG;
			}
			int posXT = s.indexOf("#");
			text = s.substring(0, posXT);
		}
		return text;
	}
}
