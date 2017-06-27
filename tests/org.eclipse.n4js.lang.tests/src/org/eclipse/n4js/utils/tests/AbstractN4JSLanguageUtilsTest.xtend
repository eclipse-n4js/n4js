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
package org.eclipse.n4js.utils.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
abstract class AbstractN4JSLanguageUtilsTest {

	@Inject private extension N4JSParseHelper parseHelper;
	@Inject private extension ValidationTestHelper;


	def protected void assertVarianceOfPosition(CharSequence code, Variance expectedVariance) {
		assertVarianceOfPosition(code, expectedVariance, false);
	}
	def protected void assertVarianceOfPositionRelativeToItsRoot(CharSequence code, Variance expectedVariance) {
		assertVarianceOfPosition(code, expectedVariance, true);
	}
	def private void assertVarianceOfPosition(CharSequence code, Variance expectedVariance, boolean relativeToItsRoot) {
		val script = code.parse;
		script.assertNoParseErrors;
		val issues = script.validate;
		assertTrue(issues.toString, issues.empty);
		val ref2TypeVars = script.eAllContents.filter(ParameterizedTypeRef).filter[declaredType instanceof TypeVariable].toList;
		assertEquals("expected exactly one reference to a type variable, but found: " + ref2TypeVars, 1, ref2TypeVars.size);
		val ref2TypeVar = ref2TypeVars.head;
		val computedVariance = if(relativeToItsRoot) {
			N4JSLanguageUtils.getVarianceOfPositionRelativeToItsRoot(ref2TypeVar)
		} else {
			N4JSLanguageUtils.getVarianceOfPosition(ref2TypeVar)
		};
		assertEquals(expectedVariance, computedVariance);
	}
}
