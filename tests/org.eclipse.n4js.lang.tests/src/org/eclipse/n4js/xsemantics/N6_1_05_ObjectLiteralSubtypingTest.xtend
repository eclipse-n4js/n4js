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

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * N4JS Spec Test: 6.1.5. Object Literal, Subtyping Type Inference
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_05_ObjectLiteralSubtypingTest extends AbstractTypesystemTest {

	static val prefix = '''
		public class C {
			public s: string;
			n: number;
			public foo(): void {}
		}
	''';

	@Test
	def void testSubtypeSameTypes() {
		assertSubtypeReflexive('''a: Object''', '''b: Object''')
		assertSubtypeReflexive('''a = {}''', '''b = {}''')
		assertSubtypeReflexive('''a = {s: "Hello",n: 42}''', '''a = {s: "Hello",n: 42}''');
		assertSubtypeReflexive('''a = {s: "Hello",n: 42}''', '''a = {n: 42,s: "Hello"}''');
	}

	@Test
	def void testSubtypeObject_EmptyOL() {
		assertSubtype('''a: Object''', '''b = {}''')
	}

	@Test
	def void testSubtypeEmptyOL_Object() {
		assertSubtype('''b = {}''', '''a: Object''')
	}

	@Test
	def void testSubtypeOLWithMembers_Object() {
		assertSubtype('''b = {s: "Hello",n: 42}''', '''a: Object''')
	}

	@Test
	def void testSubtypeObject_OLWithMembers() {
		assertSubtypeNonReflexive('''b = {s: "Hello",n: 42}''', '''a: Object''')
	}

	@Test
	def void testSubtypeObjectWithMembers_OLWithMembers() {
		assertSubtypeReflexive('''a: ~Object with {s: string; n: number;}''', '''b = {s: "Hello",n: 42}''')
	}

	@Test
	def void testSubtypeOLsMoreMembers() {
		assertSubtypeNonReflexive('''a = {s: "Hello",n: 42}''', '''b = {}''')
	}

	@Test
	def void testSubtypeOLsMoreMembers2() {
		assertSubtypeNonReflexive('''a = {s: "Hello",n: 42}''', '''b = {n: 42}''')
		assertSubtypeNonReflexive('''a = {s: "Hello",n: 42}''', '''b = {s: "Hello"}''')
	}

	@Test
	def void testSubtypeOLsUndefNull() {
		assertSubtypeReflexive('''a = {s: null}''', '''b = {s: null}''')
		assertSubtypeReflexive('''a = {s: undefined}''', '''b = {s: null}''')
		assertSubtypeReflexive('''a = {s: undefined}''', '''b = {s: undefined}''')
	}

	@Test
	def void testSubtypeOLsDifferentMembers() {
		assertNeigtherSubtypeNorSupertype('''a = {s: "Hello"}''', '''b = {s:42}''')
		assertNeigtherSubtypeNorSupertype('''a = {s: "Hello"}''', '''b = {s:null}''')
		assertNeigtherSubtypeNorSupertype('''a = {s: "Hello"}''', '''b = {s:undefined}''')
	}

	@Test
	def void testSubtypeOLsClass() {
		assertSubtypeReflexive('''a = {s: "Hello"}''', '''b: ~~C''') // only s
		assertSubtypeNonReflexive('''b: ~C''', '''a = {s: "Hello"}''') // foo is missing in OL
		assertNotSubtype('''a = {s: 42}''', '''b: ~~C''') // wrong type of s
	}

	@Test
	def void testSubtypeOLsClassWithFucntion() {
		// non reflexive: methods are read only, so a property can used instead of a method, but not vice versa
		assertSubtypeNonReflexive('''a = {s: "Hello", foo: function(): void {}}''', '''b: ~C''')
	}



	def void assertNeigtherSubtypeNorSupertype(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, false)
		assertSubtype(rightVarDecl, leftVarDecl, false)
	}

	def void assertSubtypeNonReflexive(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, true)
		assertSubtype(rightVarDecl, leftVarDecl, false)
	}

	def void assertSubtypeReflexive(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, true)
		assertSubtype(rightVarDecl, leftVarDecl, true)
	}

	def void assertSubtype(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, true)
	}

	def void assertNotSubtype(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, false)
	}

	def void assertSubtype(CharSequence leftVarDecl, CharSequence rightVarDecl, boolean expectedResult) {
		val script = createScript(JavaScriptVariant.n4js,
			'''
				«prefix»
				var «leftVarDecl»;
				var «rightVarDecl»;
			''');
		val decls = EcoreUtil2.getAllContentsOfType(script, VariableDeclaration);
		val left = decls.get(0);
		val right = decls.get(1);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		val typeRefLeft = ts.type(G, left);
		val typeRefRight = ts.type(G, right);

		assertSubtype(G, typeRefLeft, typeRefRight, expectedResult);

	}

}
