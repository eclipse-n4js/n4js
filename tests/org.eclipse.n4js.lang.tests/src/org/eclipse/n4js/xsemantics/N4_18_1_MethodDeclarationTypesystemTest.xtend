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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N4_18_1_MethodDeclarationTypesystemTest extends AbstractTypesystemTest {

	@Inject extension ValidationTestHelper

	@Test
	def void testMethodDeclarationEmpty() {
		'''
			m() {}
		'''.
		assertTypeOfMethodDeclaration("{function():void}", 0)
	}

	@Test
	def void testMethodDeclarationWithoutParamType() {
		'''
			f(i) { return i; }
		'''.
		assertTypeOfMethodDeclaration("{function(any):any}", 0)
	}

	@Test
	def void testMethodDeclarationWithoutParams() {
		'''
			f() { return 0; }
		'''.
		assertTypeOfMethodDeclaration("{function():any}", 0)
//		assertTypeOfMethodDeclaration("{function():number}", 0) // use this after fixing IDE-1049
	}

	@Test
	def void testMethodDeclarationRecursiveWithoutTypes() {
		'''
			f() { return this.f(); }
		'''.
		assertTypeOfMethodDeclaration("{function():any}", 0)
	}

	def private void assertTypeOfMethodDeclaration(CharSequence method, String expectedTypeName, int expectedIssues) {
		val script = createScript(JavaScriptVariant.n4js,
			'''
			class C {
				«method»
			}
			'''
		)
		val funcExpr = EcoreUtil2.getAllContentsOfType(
			script,
			N4MethodDeclaration
		).last

		assertTypeName(expectedTypeName, funcExpr);

		val issues = script.validate();
		assertIssueCount(expectedIssues, issues);
	}
}
