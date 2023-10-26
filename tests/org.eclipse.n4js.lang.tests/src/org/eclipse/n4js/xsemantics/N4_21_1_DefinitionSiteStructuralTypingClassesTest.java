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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4_21_1_DefinitionSiteStructuralTypingClassesTest extends AbstractTypesystemTest {

	@Test
	public void testSubtypeSubclass() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class Nominal{
					public s: string;
					public foo(): void {}
				}
				class NonN4 extends Object {
					public s: string;
					public foo(): void {}
				}
				class A{
					public s: string
					public foo(): void{}
				}
				class B{
					public s: string;
				}
				var nominal: Nominal;
				var structural: NonN4;
				var a: A;
				var b: B;
				""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef nominal = varDeclTypeByName(script, "nominal");
		TypeRef structural = varDeclTypeByName(script, "structural");
		TypeRef a = varDeclTypeByName(script, "a");
		TypeRef b = varDeclTypeByName(script, "b");

		assertSubtype(G, a, nominal, false);
		assertSubtype(G, b, nominal, false);

		// N4Object do not work on definition site:
		assertSubtype(G, a, structural, false);
		assertSubtype(G, b, structural, false);
	}

	@Test
	public void testSubtypeSubclassSameTypeDeclared() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class Structural{
					public s: string;
					public foo(): void {}
				}
				var structural: Structural;
				""");
		RuleEnvironment G = newRuleEnvironment(script);
		TypeRef structural = varDeclTypeByName(script, "structural");
		assertSubtype(G, structural, structural, true);
	}

	/**
	 * If this test fails, then we cannot expect {@link #testSubtypeSubclassSameTypeInferred()} to succeed.
	 */
	@Test
	public void testSubtypeSubclassSameTypeInferredWithNominalTyping() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class Nominal{
					public s: string;
					public foo(): void {}
				}
				var nominal: Nominal;
				var n = new Nominal();
				""");
		RuleEnvironment G = newRuleEnvironment(script);
		TypeRef nominal = varDeclTypeByName(script, "nominal");
		VariableDeclaration n = varDeclByName(script, "n");
		TypeRef nType = ts.type(G, n);
		assertSubtype(G, nType, nominal, true);
	}

	@Test
	public void testSubtypeSubclassSameTypeInferred() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class Structural{
					public s: string;
					public foo(): void {}
				}
				var structural: Structural;
				var s = new Structural();
				""");
		RuleEnvironment G = newRuleEnvironment(script);
		TypeRef structural = varDeclTypeByName(script, "structural");
		VariableDeclaration s = varDeclByName(script, "s");
		TypeRef sType = ts.type(G, s);
		assertSubtype(G, sType, structural, true);
	}

	@Test
	public void testSubtypeSubclassWithExternalClass() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.external, """
				external class Nominal{
					public s: string;
					public foo(): void
				}
				external class Structural{
					public s: string;
					public foo(): void
				}
				external class A{
					public s: string;
					public foo(): void
				}
				external class B{
					public s: string;
				}
				var nominal: Nominal;
				var structural: ~Structural;
				var a: A;
				var b: B;
				""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef nominal = varDeclTypeByName(script, "nominal");
		TypeRef structural = varDeclTypeByName(script, "structural");
		TypeRef a = varDeclTypeByName(script, "a");
		TypeRef b = varDeclTypeByName(script, "b");

		assertSubtype(G, b, nominal, false);
		assertSubtype(G, a, nominal, false);

		// external classes are derived from Object!
		assertSubtype(G, a, structural, true);

		// still, it must implement everything
		assertSubtype(G, b, structural, false);
	}

	private TypeRef varDeclTypeByName(Script script, String name) {
		return varDeclByName(script, name).getDeclaredTypeRef();
	}

	private VariableDeclaration varDeclByName(Script script, String name) {
		return ((VariableStatement) findFirst(script.getScriptElements(), se ->

		(se instanceof VariableStatement)
				&& Objects.equals(((VariableStatement) se).getVarDecl().get(0).getName(), name)

		)).getVarDecl().get(0);
	}

}
