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

import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * see IDE-414
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TypeHintTest extends AbstractTypesystemTest {

	@Test
	public void testNonNormalizationOfJoin() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class A{}
				class B extends A{}
				class C{}
				var a: A;
				var b: B;
				var c: C;

				// v = join
				// Case 1:
				var x1 /* no type */ = true ? a : b; // x should have type A (as A v B = A)
				// Case 2:
				var y1: union{A,B} = true? a : b; // assignment should work already
				// Case 3:
				var x2 /* no type */ = true? a : c; // x should have type N4Object (as A v C = N4Object)
				// Case 4:
				var y2: union{A,C} = true? a : c; // rhs needs type hint!

				""");

		List<VariableDeclaration> varDecls = EcoreUtil2.getAllContentsOfType(script, VariableDeclaration.class);
		Iterator<VariableDeclaration> vi = varDecls.iterator();

		VariableDeclaration a = vi.next();
		VariableDeclaration b = vi.next();
		VariableDeclaration c = vi.next();
		// VariableDeclaration x1 = vi.next(); // probably not needed, as case is emulated
		// VariableDeclaration y1 = vi.next();
		// VariableDeclaration x2 = vi.next();
		// VariableDeclaration y2 = vi.next();

		assertTypeName("A", a);
		assertTypeName("B", b);
		assertTypeName("C", c);

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		assertEquals("A", tsh.join(G, a.getDeclaredTypeRef(), b.getDeclaredTypeRef()).getTypeRefAsString());
		assertEquals("N4Object", tsh.join(G, a.getDeclaredTypeRef(), c.getDeclaredTypeRef()).getTypeRefAsString());

	}

}
