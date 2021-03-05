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
package org.eclipse.n4js.typesystem

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for {@link TypeSystemHelper#join(RuleEnvironment, TypeRef...)} method with simple declared types.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JoinComputer__RawTypesTest extends AbstractTypeSystemHelperTests {

	@Test
	def void testJoin() {
		setScriptTypeDefinitions('''
			class A{}
			class B extends A {}
			class C extends B {}
			class D extends C {}
		''')
		assertJoin("A", "A");
		assertJoin("B", "B");
		assertJoin("A", "A", "B");
		assertJoin("A", "B", "A");
		assertJoin("B", "B", "C");
		assertJoin("B", "C", "B");
		assertJoin("B", "B", "C", "D");
		assertJoin("B", "D", "B", "C");
		assertJoin("C", "C", "D");
		assertJoin("C", "D", "C");
		assertJoin("A", "A", "B", "C", "D");
		assertJoin("A", "D", "C", "B", "A");
	}

	@Test
	def void testJoin2() {
		setScriptTypeDefinitions('''
			class A{}
			interface B{}
			class C extends A {}
			interface  D extends B {}
			class E extends C implements D {}
			class F implements D {}
		''')
		assertJoin("A", "A");
		assertJoin("B", "B");
		assertJoin("D", "D", "E");
		assertJoin("D", "D", "F");
		assertJoin("D", "E", "F");
	}

	@Test
	def void testJoinIntersection() {
		setScriptTypeDefinitions('''
			class A{}
			interface B{}
			class C extends A {}
			interface  D extends B {}
			class E extends C implements D {}
			class F extends A implements D {}
		''')
		assertJoin("A", "A");
		assertJoin("B", "B");
		assertJoin("D", "D", "E");
		assertJoin("D", "D", "F");
		assertJoin("intersection{A,D}", "E", "F");
	}

	@Test
	def void testJoinIntersection2() {
		setScriptTypeDefinitions('''
			class A {}
			class B extends A{}
			class C extends B {}
			interface I {}
			class D extends C implements I {}
			class E extends B implements I {}
			class F extends E {}
		''')
		assertJoin("B", "C", "E");
		assertJoin("B", "B", "C", "E");
		assertJoin("intersection{B,I}", "D", "F");
	}

	@Test
	def void testJoinWithBiultin() {
		setScriptTypeDefinitions('''
			class A{}
			class B{}
		''')

		// not String or string!
		assertJoin("any", "String", "string");

		// implicit super types
		assertJoin("Object", "A", "String");
		assertJoin("N4Object", "A", "B");

		assertJoin("any", "A", "string");
	}

}
