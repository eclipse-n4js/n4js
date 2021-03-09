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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/*
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N4_21_1_DefinitionSiteStructuralTypingInterfacesTest extends AbstractTypesystemTest {

	@Test
	def void testSubtype() {

		val script = createAndValidateScript(JavaScriptVariant.n4js, '''
			interface Nominal{
				public s: string;
				public foo(): void
			}
			interface ~Structural{
				public s: string;
				public foo(): void
			}
			class A {
				public s: string;
				public foo(): void {}
			}
			class B {
				public s: string;
			}
			var nominal: Nominal;
			var structural: Structural;
			var a: A;
			var b: B;
		''')

		val G = newRuleEnvironment(script);

		val nominal = script.varDeclTypeByName("nominal")
		val structural = script.varDeclTypeByName("structural")
		val a = script.varDeclTypeByName("a")
		val b = script.varDeclTypeByName("b")

		assertSubtype(G, a, nominal, false)
		assertSubtype(G, b, nominal, false)

		// N4Object do not work on definition site:
		assertSubtype(G, a, structural, false)
		assertSubtype(G, b, structural, false)
	}

	@Test
	def void testSubtypeSameTypeDeclared() {
		val script = createAndValidateScript(JavaScriptVariant.n4js, '''
			interface ~Structural{
				public s: string;
				public foo(): void
			}
			var structural: Structural;
		''')
		val G = newRuleEnvironment(script);
		val structural = script.varDeclTypeByName("structural")
		assertSubtype(G, structural, structural, true)
	}

	@Test
	def void testSubtypeSubclassWithExternalClass() {

		// class A{}
		val script = createAndValidateScript(JavaScriptVariant.external, '''
			external interface Nominal{
				public s: string;
				public foo(): void
			}
			interface ~Structural{
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
			var structural: Structural;
			var a: A;
			var b: B;
		''')

		val G = newRuleEnvironment(script);

		val nominal = script.varDeclTypeByName("nominal")
		val structural = script.varDeclTypeByName("structural")
		val a = script.varDeclTypeByName("a")
		val b = script.varDeclTypeByName("b")

		assertSubtype(G, b, nominal, false)
		assertSubtype(G, a, nominal, false)

		// external classes are derived from Object!
		assertSubtype(G, a, structural, true)

		// still, it must implement everything
		assertSubtype(G, b, structural, false)
	}

	private def TypeRef varDeclTypeByName(Script script, String name) {
		(script.scriptElements.findFirst [
			if (it instanceof VariableStatement) {
				it.varDecl.head.name == name
			} else {
				false
			}
		] as VariableStatement).varDecl.head.declaredTypeRef
	}



}
