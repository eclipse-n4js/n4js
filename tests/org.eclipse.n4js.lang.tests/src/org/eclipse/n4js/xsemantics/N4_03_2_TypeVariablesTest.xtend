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

import org.eclipse.n4js.typesystem.AbstractTypeSystemHelperTests
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.typesystem.SubTypeRelationForTest.*
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 * N4JS Spec Test: 4.3.2. Type Variables
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class N4_03_2_TypeVariablesTest extends AbstractTypeSystemHelperTests {

	final static CharSequence scriptPrefix =
		'''
			class A{}
			class B extends A{}
			class C extends B{}
		'''


	@Test
	def void testTypeVarInGenericWithoutBounds() {
		assembler.setScriptPrefix('''
			«scriptPrefix»
			class G<T> {
				typevar: T;
				foo(): void {
					this.typevar;
				}
			}
		''')
		assertSubTypeOfRef("typevar", _sub, "any");
	}


	@Test
	def void testTypeVarInGenericWithBound() {
		assembler.setScriptPrefix('''
			«scriptPrefix»
			class G<T extends A> {
				typevar: T;
			 	foo(): void {
					this.typevar;
				}
			}
		''')
		assertSubTypeOfRef("typevar", _sub, "A");
	}

	@Test
	def void testTypeVarInGenericFunctionWOBounds() {
		assembler.setScriptPrefix('''
			«scriptPrefix»
			function <T> foo(typevar: T) {
				typevar;
			}
		''')
		assertSubTypeOfRef("typevar", _sub, "any");
	}

	@Test
	def void testTypeVarInGenericFunctionWithBounds() {
		assembler.setScriptPrefix('''
			«scriptPrefix»
			function <T extends A> foo(typevar: T) {
				typevar;
			}
		''')
		assertSubTypeOfRef("typevar", _sub, "A");
	}


}
