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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * @see IDE-414
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TypeHintTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	@Inject
	extension TypeSystemHelper


	@Test
	def void testNonNormalizationOfJoin() {
		val script = '''
			class A{}
			class B extends A{}
			class C{}
			var a: A;
			var b: B;
			var c: C;

			// v = join
			// Case 1:
			var /* no type */ x1 = true ? a : b; // x should have type A (as A v B = A)
			// Case 2:
			var union{A,B}    y1 = true? a : b; // assignment should work already
			// Case 3:
			var /* no type */ x2 = true? a : c; // x should have type N4Object (as A v C = N4Object)
			// Case 4:
			var union{A,C}    y2 = true? a : c; // rhs needs type hint!

		'''.parse()

		val varDecls = EcoreUtil2.getAllContentsOfType(script, VariableDeclaration);
		val vi = varDecls.iterator;

		var a = vi.next
		var b = vi.next
		var c = vi.next
//		var x1 = vi.next // probably not needed, as case is emulated
//		var y1 = vi.next
//		var x2 = vi.next
//		var y2 = vi.next

		assertTypeName("A", a);
		assertTypeName("B", b);
		assertTypeName("C", c);


		val G = RuleEnvironmentExtensions.newRuleEnvironment(script)
		assertEquals("A", join(G, a.declaredTypeRef, b.declaredTypeRef).typeRefAsString);
		assertEquals("N4Object", join(G, a.declaredTypeRef, c.declaredTypeRef).typeRefAsString);

	}

}
