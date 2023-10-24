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

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class N7_3_2_ExportStatementOfVariablesTest extends N7_1_1_VariablesTest {

	@Override
	public void assertVarDeclarationType(String expectedTypeAsString, String varDeclaration) {
		String scriptSrc = "export var " + varDeclaration + ";";
		Script script = createScript(JavaScriptVariant.n4js, scriptSrc);
		List<Issue> issues = valTestHelper.validate(script);
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc + "\n"
				+ join("\n", Issue::getMessage, issues),
				0, size(filter(issues, issue -> issue.getSeverity() != Severity.WARNING)));

		VariableDeclaration varDecl = ((VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement()).getVarDecl().get(0);
		assertNotNull("Bogus test, variable declaration not found", varDecl);

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		var typeOfVariableResult = ts.type(G, varDecl);
		assertNotNull("Cannot compute type of " + varDeclaration + ":", typeOfVariableResult);
		assertFalse("Cannot compute type of " + varDeclaration + ":" + typeOfVariableResult,
				typeOfVariableResult instanceof UnknownTypeRef);
		assertEquals(expectedTypeAsString, typeOfVariableResult.getTypeRefAsString());
	}

	@Test
	public void testUseExportedVariable() {
		String scriptSrc = """
				export var a = "x";
				var b = a;
				""";
		Script script = createScript(JavaScriptVariant.n4js, scriptSrc);
		List<Issue> issues = valTestHelper.validate(script);
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc + "\n"
				+ join("\n", Issue::getMessage, issues), 0, issues.size());
	}

	@Test
	public void testUseExportedVariable2() {
		String scriptSrc = """
				export var a;
				var b = a;
				""";
		Script script = createScript(JavaScriptVariant.n4js, scriptSrc);
		List<Issue> issues = valTestHelper.validate(script);
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc + "\n"
				+ join("\n", Issue::getMessage, issues), 0, issues.size());
	}

	// more tests: see N7_1_1_VariablesTest

}
