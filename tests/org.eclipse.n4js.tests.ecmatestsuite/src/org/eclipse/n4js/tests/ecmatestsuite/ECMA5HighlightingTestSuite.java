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
package org.eclipse.n4js.tests.ecmatestsuite;

import org.eclipse.xtext.testing.InjectWith;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import org.eclipse.n4js.JSLibSingleTestConfig;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.TestCodeProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.editor.syntaxcoloring.HighlightingParserTester;

/**
 * Tests the highlighting parser with the ECMA test suite.
 */
@RunWith(XtextParametrizedRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ECMA5HighlightingTestSuite extends ECMA5TestSuite {

	@Inject
	private HighlightingParserTester highlightingParser;

	/**
	 * @param config
	 *            the current test set
	 */
	public ECMA5HighlightingTestSuite(JSLibSingleTestConfig config) {
		super(new JSLibSingleTestConfig(config.entry, config.resourceName, null));
	}

	@Test
	@Override
	public void test() throws Exception {
		String code = TestCodeProvider.getContentsFromFileEntry(config.entry, config.resourceName);
		if (code == null) {
			throw new Error("test data code instance is null");
		}
		highlightingParser.getTokens(code);
	}
}
