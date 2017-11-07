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
import org.eclipse.xsemantics.runtime.TraceUtils
import org.junit.Before

import static org.junit.Assert.*

/**
 */
abstract class AbstractJudgmentSubtypeTest extends AbstractTypesystemTest {

	@Inject
	TypeRefsToVariablesAssembler assembler;

	@Inject extension TraceUtils

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
			if (result.ruleFailedException !== null) {
				fail(
					leftTypeExpr + "<:" + rightTypeExpr + " should work, but: " +
						result.ruleFailedException.failureTraceAsString())
			}
			assertNotNull(leftTypeExpr + "<:" + rightTypeExpr + " should work", result.value)
			assertTrue(leftTypeExpr + "<:" + rightTypeExpr + " should work", result.value)
		} else {
			assertNotNull(leftTypeExpr + "<:" + rightTypeExpr + " should fail", result.ruleFailedException);
			assertNull(result.value)
		}
	}

}
