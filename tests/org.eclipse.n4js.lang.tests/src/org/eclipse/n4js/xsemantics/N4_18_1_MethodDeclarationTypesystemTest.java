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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4_18_1_MethodDeclarationTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testMethodDeclarationEmpty() {
		assertTypeOfMethodDeclaration("""
				m() {}
				""", "{function():void}", 0);
	}

	@Test
	public void testMethodDeclarationWithoutParamType() {
		assertTypeOfMethodDeclaration("""
				f(i) { return i; }
				""", "{function(any):any}", 0);
	}

	@Test
	public void testMethodDeclarationWithoutParams() {
		assertTypeOfMethodDeclaration("""
				f() { return 0; }
				""", "{function():any}", 0);
		// assertTypeOfMethodDeclaration("{function():number}", 0); // use this after fixing IDE-1049
	}

	@Test
	public void testMethodDeclarationRecursiveWithoutTypes() {
		assertTypeOfMethodDeclaration("""
				f() { return this.f(); }
				""", "{function():any}", 0);
	}

	private void assertTypeOfMethodDeclaration(CharSequence method, String expectedTypeName, int expectedIssues) {
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class C {
							%s
						}
						""".formatted(method));

		N4MethodDeclaration funcExpr = last(EcoreUtil2.getAllContentsOfType(
				script, N4MethodDeclaration.class));

		assertTypeName(expectedTypeName, funcExpr);

		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(expectedIssues, issues);
	}
}
