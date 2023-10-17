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
package org.eclipse.n4js.tests.parser.regex;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.analysis.SmokeTester;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class RegexInN4JSSmokeTest extends RegexInN4JSParserTest {

	@Inject
	SmokeTester smokeTester;

	@Override
	public void assertValid(CharSequence expression) {
		try {
			smokeTester.assertNoException(expression);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
