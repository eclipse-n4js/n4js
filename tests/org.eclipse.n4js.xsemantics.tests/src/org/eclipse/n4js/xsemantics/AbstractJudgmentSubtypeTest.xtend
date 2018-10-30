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

import com.google.inject.Inject
import org.eclipse.n4js.typesystem.TypeRefsToVariablesAssembler
import org.junit.Before

import static org.junit.Assert.*

/**
 */
abstract class AbstractJudgmentSubtypeTest extends AbstractTypesystemTest {

	@Inject
	TypeRefsToVariablesAssembler assembler;

	@Before
	def prepareScript() {
		assembler.setScriptPrefix(
			'''
				class A{}
				class B extends A {}
				class C extends B {}
				class D {}
				class E {}
			''')
	}

	/**
	 * Assert that left is a sub type of right, i.e. left <: right is true
	 */
	def assertSubType(String leftTypeExpr, String rightTypeExpr) {
		assertSubType(leftTypeExpr, rightTypeExpr, true);
	}

	def assertSubType(String leftTypeExpr, String rightTypeExpr, boolean expectedResult) {
		val G = assembler.prepareScriptAndCreateRuleEnvironment(leftTypeExpr, rightTypeExpr)
		val left = assembler.getTypeRef(leftTypeExpr);
		val right = assembler.getTypeRef(rightTypeExpr);

		var result = ts.subtype(G, left, right);
		if (expectedResult) {
			if (result.failure) {
				fail(
					leftTypeExpr + "<:" + rightTypeExpr + " should work, but: " +
						result.failureMessage)
			}
			assertNotNull(leftTypeExpr + "<:" + rightTypeExpr + " should work", result.value)
			assertTrue(leftTypeExpr + "<:" + rightTypeExpr + " should work", result.value)
		} else {
			assertTrue(leftTypeExpr + "<:" + rightTypeExpr + " should fail", result.failure);
			assertNull(result.value)
		}
	}

}
