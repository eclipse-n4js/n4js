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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * @see N7_3_2_ExportStatementOfVariablesTest
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N7_1_1_VariablesTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	public void assertVarDeclarationType(String expectedTypeAsString, String varDeclaration) {
		String scriptSrc = "var " + varDeclaration + ";";
		Script script = createScript(JavaScriptVariant.n4js, scriptSrc);
		List<Issue> issues = valTestHelper.validate(script);
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc, 0,
				size(filter(issues, issue -> issue.getSeverity() != Severity.WARNING)));
		VariableDeclaration varDecl = ((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0);
		assertNotNull("Bogus test, variable declaration not found", varDecl);

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		TypeRef typeOfVariableResult = ts.type(G, varDecl);
		assertNotNull("Cannot compute type of " + varDeclaration + ":", typeOfVariableResult);
		assertFalse("Cannot compute type of " + varDeclaration + ":" + typeOfVariableResult,
				typeOfVariableResult instanceof UnknownTypeRef);
		assertEquals(expectedTypeAsString, typeOfVariableResult.getTypeRefAsString());
	}

	@Test
	public void testTypeAnyVars() {
		assertVarDeclarationType("any", "a: any");
		assertVarDeclarationType("any", "any: any");
		assertVarDeclarationType("any", "a");
		assertVarDeclarationType("any", "a: any = \"hello\"");
		assertVarDeclarationType("any", "a: any = 1");
		assertVarDeclarationType("any", "a: any = null");
		assertVarDeclarationType("any", "a: any = undefined");

	}

	@Test
	public void testTypeDeclaredVars() {
		assertVarDeclarationType("string", "s: string");
		assertVarDeclarationType("string", "s: string = \"Hello\"");
		assertVarDeclarationType("string", "s: string = null");
		assertVarDeclarationType("string", "s: string = undefined");
		assertVarDeclarationType("union{number,string}", "s: union{number,string}");
		assertVarDeclarationType("union{number,string}", "s: union{number,string} = \"Hello\"");
		assertVarDeclarationType("union{number,string}", "s: union{number,string} = 42");
	}

	@Test
	public void testTypeInferredToAnyVars() {
		assertVarDeclarationType("any", "a");
		assertVarDeclarationType("any", "a = null");
		assertVarDeclarationType("any", "a = undefined");
	}

	@Test
	public void testTypeInferredVars() {
		assertVarDeclarationType("string", "s = \"Hello\"");
		assertVarDeclarationType("int", "i = 42");
		assertVarDeclarationType("union{string,int}", "u = (true)?\"Hello\":42");
		assertVarDeclarationType("union{string,int}", "u = \"Hello\" || 42");
	}
}
