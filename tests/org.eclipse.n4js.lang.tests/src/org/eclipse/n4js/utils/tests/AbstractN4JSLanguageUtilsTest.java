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
package org.eclipse.n4js.utils.tests;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
abstract public class AbstractN4JSLanguageUtilsTest {

	@Inject
	private N4JSParseHelper parseHelper;
	@Inject
	private ValidationTestHelper valTestHelper;

	protected void assertVarianceOfPosition(CharSequence code, Variance expectedVariance) {
		assertVarianceOfPosition(code, expectedVariance, false);
	}

	protected void assertVarianceOfPositionRelativeToItsRoot(CharSequence code, Variance expectedVariance) {
		assertVarianceOfPosition(code, expectedVariance, true);
	}

	private void assertVarianceOfPosition(CharSequence code, Variance expectedVariance, boolean relativeToItsRoot) {
		try {
			Script script = parseHelper.parse(code);
			parseHelper.assertNoParseErrors(script);
			List<Issue> issues = valTestHelper.validate(script);
			assertTrue(join("\n", issues), issues.isEmpty());
			List<ParameterizedTypeRef> ref2TypeVars = toList(
					filter(filter(script.eAllContents(), ParameterizedTypeRef.class),
							tr -> tr.getDeclaredType() instanceof TypeVariable));

			assertEquals("expected exactly one reference to a type variable, but found: " + ref2TypeVars, 1,
					ref2TypeVars.size());
			ParameterizedTypeRef ref2TypeVar = ref2TypeVars.get(0);
			Variance computedVariance = relativeToItsRoot
					? N4JSLanguageUtils.getVarianceOfPositionRelativeToItsRoot(ref2TypeVar)
					: N4JSLanguageUtils.getVarianceOfPosition(ref2TypeVar);
			assertEquals(expectedVariance, computedVariance);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
