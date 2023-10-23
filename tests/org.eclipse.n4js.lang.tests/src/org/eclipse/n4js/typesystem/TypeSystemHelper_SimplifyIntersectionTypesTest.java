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
package org.eclipse.n4js.typesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Tests for {@link org.eclipse.n4js.typesystem.XsemanticsTypeSystemHelper.createIntersectionType(RuleEnvironment, TypeRef)} method with intersection types.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class TypeSystemHelper_SimplifyIntersectionTypesTest extends AbstractTypeSystemHelperTests {

	@Before
	public void prepareTypeDefs() {
		setDefaultTypeDefinitions();
	}

	/**
	 * Asserts that join of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	void assertSimplify(String expectedType, String typeExpressionsToBeSimplified) {
		assertSimplify(expectedType, typeExpressionsToBeSimplified, new String[0]);
	}

	void assertSimplify(String expectedType, String typeExpressionsToBeSimplified, String... expectedIssueMsg) {
		RuleEnvironment G = assembler.prepareScriptAndCreateRuleEnvironment(expectedIssueMsg,
				typeExpressionsToBeSimplified);
		TypeRef typeRef = assembler.getTypeRef(typeExpressionsToBeSimplified);
		assertTrue("Error in test setup, expected intersection type", typeRef instanceof IntersectionTypeExpression);
		TypeRef simplified = TypeUtils.copy(tsh.simplify(G, (IntersectionTypeExpression) typeRef));
		assertEquals(expectedType, simplified.getTypeRefAsString());
	}

	@Test
	public void testSimplifyDuplicates() {
		assertSimplify("A", "intersection{A}");
		assertSimplify("I2", "intersection{I1,I2}");
		assertSimplify("I2", "intersection{I1,I2,I1}");
	}

	@Test
	public void testSimplifyNestedIntersections() {
		assertSimplify("I2", "intersection{I1,I2,intersection{I1,I2}}");
		assertSimplify("intersection{I1,I2,I3}", "intersection{I1,I2,intersection{I2,I3}}");
	}

	@Test
	public void testSimplifyUndefinedAndNull() {
		assertSimplify("undefined", "intersection{I1,I2,undefined}");
		assertSimplify("undefined", "intersection{I1,undefined,I2}");
		assertSimplify("undefined", "intersection{A,undefined}");
		assertSimplify("undefined", "intersection{undefined,A}");
		assertSimplify("undefined", "intersection{undefined,undefined}");
	}

	@Test
	public void testSimplifyObjectAndOther() {
		assertSimplify("{function():void}", "intersection{Object,() => void}");
		assertSimplify("Function", "intersection{Object,Function}", IssueCodes.INTER_REDUNDANT_SUPERTYPE);
		assertSimplify("intersection{~Object,{function():void}}", "intersection{~Object,() => void}");
		assertSimplify("intersection{~Object,Function}", "intersection{~Object,Function}");
	}

}
