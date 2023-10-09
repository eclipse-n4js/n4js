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
package org.eclipse.n4js.regex.tests;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.regex.RegularExpressionInjectorProvider;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(RegularExpressionInjectorProvider.class)
public class RegexExampleTest extends AbstractRegexExampleTest {

	@Inject
	ParseHelper<RegularExpressionLiteral> parseHelper;

	@Override
	public void assertValid(CharSequence expression) {
		RegularExpressionLiteral parsed;
		try {
			parsed = parseHelper.parse(expression);
			EList<Diagnostic> errors = parsed.eResource().getErrors();
			assertTrue(errors.toString(), errors.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
