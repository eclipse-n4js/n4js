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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.validation.Issue;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N7_3_2_ExportStatementOfConstsTest extends N7_3_1_ConstTest {

	@Override
	public void assertVarDeclarationType(String expectedTypeAsString, String varDeclaration) {
		String scriptSrc = "export const " + varDeclaration + ";";
		Script script = createScript(JavaScriptVariant.n4js, scriptSrc);
		List<Issue> issues = valTestHelper.validate(script);
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc, 0, issues.size());
		VariableDeclaration varDecl = ((VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement()).getVarDecl().get(0);
		assertNotNull("Bogus test, variable declaration not found", varDecl);

		assertTrue("Expected const declaration", varDecl.isConst());
		assertNotNull("Bogus test, initializer of const must not be null", varDecl.getExpression());

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		TypeRef typeOfVariableResult = ts.type(G, varDecl);
		assertNotNull("Cannot compute type of " + varDeclaration + ":", typeOfVariableResult);
		assertFalse("Cannot compute type of " + varDeclaration + ":" + typeOfVariableResult,
				typeOfVariableResult instanceof UnknownTypeRef);
		assertEquals(expectedTypeAsString, typeOfVariableResult.getTypeRefAsString());
	}

	// Tests: see N7_1_1_VariablesTest

}
