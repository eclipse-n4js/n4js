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
package org.eclipse.n4js.xsemantics

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JudgmentSubtypeRefForClassifiersTest extends AbstractJudgmentSubtypeTest {

	@Test
	def void testSubTypeSameStaticType() {
		assertSubType("/*1*/type{A}", "/*2*/type{A}");
	}

	@Test
	def void testSubTypeInheritedStaticType() {
		assertSubType("/*1*/type{B}", "/*2*/type{A}");
		assertSubType("/*1*/type{C}", "/*2*/type{A}");
	}

	@Test
	def void testNoSubType() {
		assertSubType("/*1*/type{A}", "/*2*/type{B}", false);
		assertSubType("/*1*/type{C}", "/*2*/type{D}", false);
		assertSubType("/*1*/type{E}", "/*2*/type{D}", false);
	}

}
