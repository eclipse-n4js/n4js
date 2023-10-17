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

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.regex.tests.AbstractRegexExampleTest;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class RegexInN4JSExampleTest extends AbstractRegexExampleTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Override
	public void assertValid(CharSequence expression) {
		Script parsed;
		try {
			parsed = parseHelper.parse(expression);
			List<Diagnostic> errors = parsed.eResource().getErrors();
			assertTrue(errors.toString(), errors.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
