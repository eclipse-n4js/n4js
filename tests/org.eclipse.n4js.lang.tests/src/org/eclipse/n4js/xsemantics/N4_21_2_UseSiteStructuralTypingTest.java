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
public class N4_21_2_UseSiteStructuralTypingTest extends AbstractTypesystemTest {

	@Test
	public void testClassesOnly() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class C{
					public s: string;
					public foo(): void {}
				}
				class A{
					public s: string;
					public foo(): void {}
				}
				class B{
					public s: string;
				}
				var nominal: C;
				var structural: ~C;
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
		assertSubtype(G, a, structural, true);
		assertSubtype(G, b, structural, false);
	}

	@Test
	public void testClassesOnlyWithWrongFunctionType() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class C{
					public s: string;
					public foo(): void {}
				}
				class A{
					public s: string;
					public foo(p: number): void {}
				}

				var structural: ~C;
				var a: A;
				""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef structural = varDeclTypeByName(script, "structural");
		TypeRef a = varDeclTypeByName(script, "a");

		assertSubtype(G, a, structural, false);
	}

	@Test
	public void testClassesOnlyWithWrongTypes() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js, """
				class C{
					public s: string;
					public foo(): void {}
				}
				class A{
					public s: string;
					public foo(p: number): void {}
				}
				class B{
					public s: string;
				}
				var nominal: C;
				var structural: ~C;
				var fields: ~~C;
				var a: A;
				var b: B;
				""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef nominal = varDeclTypeByName(script, "nominal");
		TypeRef structural = varDeclTypeByName(script, "structural");
		TypeRef fields = varDeclTypeByName(script, "fields");
		TypeRef a = varDeclTypeByName(script, "a");
		TypeRef b = varDeclTypeByName(script, "b");

		assertSubtype(G, a, nominal, false);
		assertSubtype(G, b, nominal, false);
		assertSubtype(G, a, structural, false);
		assertSubtype(G, b, structural, false);
		assertSubtype(G, a, fields, true);
		assertSubtype(G, b, fields, true);
	}

	private TypeRef varDeclTypeByName(Script script, String name) {
		return ((VariableStatement) findFirst(script.getScriptElements(), se ->

		(se instanceof VariableStatement)
				&& Objects.equals(((VariableStatement) se).getVarDecl().get(0).getName(), name)

		)).getVarDecl().get(0).getDeclaredTypeRef();
	}

}
