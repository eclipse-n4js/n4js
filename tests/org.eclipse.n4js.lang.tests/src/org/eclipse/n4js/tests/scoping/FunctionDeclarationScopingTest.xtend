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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests function declaration specific functionality, such as type variables.
 *
 * @see IDE-346
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class FunctionDeclarationScopingTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void testTypeVariableBindingLocalVars() {
		val script = '''
			function <T> f1() {
			    var t: T;
			}
		'''.parse
		script.assertNoErrors
		val tfunc = script.eAllContents.filter(FunctionDeclaration).head.definedType as TFunction
		val t = script.eAllContents.filter(VariableDeclaration).head
		assertEquals(t.declaredTypeRefInAST.declaredType, tfunc.typeVars.head)
	}

	@Test
	def void testTypeVariableBindingParameters() {
		val script = '''
			function <T> f1(t: T, u: U): T {
			    return null;
			}
		'''.parse
		// script.assertNoErrors // we expect errors, U cannot be bound
		val funcDecl = script.eAllContents.filter(FunctionDeclaration).head;
		val tfunc = funcDecl.definedType as TFunction
		val typeVar = tfunc.typeVars.head
		assertEquals(funcDecl.declaredReturnTypeRefInAST.declaredType, typeVar)
		assertEquals(funcDecl.fpars.get(0).declaredTypeRefInAST.declaredType, typeVar)
		// ensure that we do not compare nonsense:
		assertNotEquals(funcDecl.fpars.get(1).declaredTypeRefInAST.declaredType, typeVar)
	}

}
