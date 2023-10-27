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

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * N4JS Spec Test: 6.1.5. Object Literal, Subtyping Type Inference
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_05_ObjectLiteralSubtypingTest extends AbstractTypesystemTest {

	static String prefix = """
			public class C {
				public s: string;
				n: number;
				public foo(): void {}
			}
			""";

	@Test
	public void testSubtypeSameTypes() {
		assertSubtypeReflexive("a: Object", "b: Object");
		assertSubtypeReflexive("a = {}", "b = {}");
		assertSubtypeReflexive("a = {s: \"Hello\",n: 42}", "a = {s: \"Hello\",n: 42}");
		assertSubtypeReflexive("a = {s: \"Hello\",n: 42}", "a = {n: 42,s: \"Hello\"}");
	}

	@Test
	public void testSubtypeObject_EmptyOL() {
		assertSubtype("a: Object", "b = {}");
	}

	@Test
	public void testSubtypeEmptyOL_Object() {
		assertSubtype("b = {}", "a: Object");
	}

	@Test
	public void testSubtypeOLWithMembers_Object() {
		assertSubtype("b = {s: \"Hello\",n: 42}", "a: Object");
	}

	@Test
	public void testSubtypeObject_OLWithMembers() {
		assertSubtypeNonReflexive("b = {s: \"Hello\",n: 42}", "a: Object");
	}

	@Test
	public void testSubtypeObjectWithMembers_OLWithMembers() {
		assertSubtypeReflexive("a: ~Object with {s: string; n: number;}", "b = {s: \"Hello\",n: 42}");
	}

	@Test
	public void testSubtypeOLsMoreMembers() {
		assertSubtypeNonReflexive("a = {s: \"Hello\",n: 42}", "b = {}");
	}

	@Test
	public void testSubtypeOLsMoreMembers2() {
		assertSubtypeNonReflexive("a = {s: \"Hello\",n: 42}", "b = {n: 42}");
		assertSubtypeNonReflexive("a = {s: \"Hello\",n: 42}", "b = {s: \"Hello\"}");
	}

	@Test
	public void testSubtypeOLsUndefNull() {
		assertSubtypeReflexive("a = {s: null}", "b = {s: null}");
		assertSubtypeReflexive("a = {s: undefined}", "b = {s: null}");
		assertSubtypeReflexive("a = {s: undefined}", "b = {s: undefined}");
	}

	@Test
	public void testSubtypeOLsDifferentMembers() {
		assertNeigtherSubtypeNorSupertype("a = {s: \"Hello\"}", "b = {s:42}");
		assertNeigtherSubtypeNorSupertype("a = {s: \"Hello\"}", "b = {s:null}");
		assertNeigtherSubtypeNorSupertype("a = {s: \"Hello\"}", "b = {s:undefined}");
	}

	@Test
	public void testSubtypeOLsClass() {
		assertSubtypeReflexive("a = {s: \"Hello\"}", "b: ~~C"); // only s
		assertSubtypeNonReflexive("b: ~C", "a = {s: \"Hello\"}");// foo is missing in OL
		assertNotSubtype("a = {s: 42}", "b: ~~C");// wrong type of s
	}

	@Test
	public void testSubtypeOLsClassWithFucntion() {
		// non reflexive: methods are read only, so a property can used instead of a method, but not vice versa
		assertSubtypeNonReflexive("a = {s: \"Hello\", foo: function(): void {}}", "b: ~C");
	}

	public void assertNeigtherSubtypeNorSupertype(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, false);
		assertSubtype(rightVarDecl, leftVarDecl, false);
	}

	public void assertSubtypeNonReflexive(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, true);
		assertSubtype(rightVarDecl, leftVarDecl, false);
	}

	public void assertSubtypeReflexive(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, true);
		assertSubtype(rightVarDecl, leftVarDecl, true);
	}

	public void assertSubtype(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, true);
	}

	public void assertNotSubtype(CharSequence leftVarDecl, CharSequence rightVarDecl) {
		assertSubtype(leftVarDecl, rightVarDecl, false);
	}

	public void assertSubtype(CharSequence leftVarDecl, CharSequence rightVarDecl, boolean expectedResult) {
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						%s
						var %s;
						var %s;
						""".formatted(prefix, leftVarDecl, rightVarDecl));
		List<VariableDeclaration> decls = EcoreUtil2.getAllContentsOfType(script, VariableDeclaration.class);
		VariableDeclaration left = decls.get(0);
		VariableDeclaration right = decls.get(1);
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		TypeRef typeRefLeft = ts.type(G, left);
		TypeRef typeRefRight = ts.type(G, right);

		assertSubtype(G, typeRefLeft, typeRefRight, expectedResult);

	}

}
