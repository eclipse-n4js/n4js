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
package org.eclipse.n4js.xsemantics;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.TypeRefsToVariablesAssembler;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.junit.Before;

import com.google.inject.Inject;

abstract public class AbstractJudgmentSubtypeTest extends AbstractTypesystemTest {

	@Inject
	TypeRefsToVariablesAssembler assembler;

	@Before
	public void prepareScript() {
		assembler.setScriptPrefix("""
				class A{}
				class B extends A {}
				class C extends B {}
				class D {}
				class E {}
				""");
	}

	/**
	 * Assert that left is a sub type of right, i.e. left <: right is true
	 */
	public void assertSubType(String leftTypeExpr, String rightTypeExpr) {
		assertSubType(leftTypeExpr, rightTypeExpr, true);
	}

	public void assertSubType(String leftTypeExpr, String rightTypeExpr, boolean expectedResult) {
		RuleEnvironment G = assembler.prepareScriptAndCreateRuleEnvironment(leftTypeExpr, rightTypeExpr);
		TypeRef left = assembler.getTypeRef(leftTypeExpr);
		TypeRef right = assembler.getTypeRef(rightTypeExpr);

		Result result = ts.subtype(G, left, right);
		if (expectedResult) {
			if (result.isFailure()) {
				fail(leftTypeExpr + "<:" + rightTypeExpr + " should work, but: " +
						result.getFailureMessage());
			}
			assertTrue(leftTypeExpr + "<:" + rightTypeExpr + " should work", result.isSuccess());
		} else {
			assertTrue(leftTypeExpr + "<:" + rightTypeExpr + " should fail", result.isFailure());
		}
	}

}
