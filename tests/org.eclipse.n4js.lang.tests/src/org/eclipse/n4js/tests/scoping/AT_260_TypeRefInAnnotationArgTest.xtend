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

import com.google.common.collect.Iterators
import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_260_TypeRefInAnnotationArgTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper
	@Inject N4JSTypeSystem ts

	@Test
	def void test_01() {
		val script = '''
			class X { x: X; }
			@This(X) function f() {
				this.x // X.x
			}
		'''.parse
		script.assertNoErrors
		val f = script.scriptElements.get(1) as FunctionDeclaration
		val x = (f.body.statements.head as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		assertSame((script.scriptElements.head as N4ClassDeclaration).definedTypeAsClass.ownedMembers.head, x.property)
	}

	@Test
	@Ignore("see IDE-496")
	def void test_02() {
		val script = '''
			class X { X x; }
			var X x
			@This(X) function f() {
				var nested = function() {
					this.x // global var x
				}
			}
		'''.parse
		script.assertNoErrors
		val x = Iterators.getOnlyElement(script.eAllContents.filter(ParameterizedPropertyAccessExpression))
		assertSame((script.scriptElements.get(1) as VariableStatement).varDecl.head, x.property)
	}

	@Test
	def void test_03() {
		val script = '''
			class X { x: X; }
			var x: X
			function f() {
				var nested = @This(X) function() {
					this.x // member X.x
				}
			}
		'''.parse
		script.assertNoErrors
		val x = Iterators.getOnlyElement(script.eAllContents.filter(ParameterizedPropertyAccessExpression))
		assertSame((script.scriptElements.head as N4ClassDeclaration).definedTypeAsClass.ownedMembers.head, x.property)
	}

	@Test
	def void test_04() {
		val script = '''
			class Y extends X { y: Y; }
			class X {
				@This(Y)
				m(): any {
					this.y
					return null;
				}
			}
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_05() {
		val script = '''
			function f() {
				var nested = @This(~Object with{a: any;}) function() {
					this.a // member record.a
				}
			}
		'''.parse
		N4JSResource.postProcessContainingN4JSResourceOf(script); // should make no difference, but maybe it helps to fix a Windows problem
		val thisRef = script.eAllContents.filter(ThisLiteral).head;
		assertNotNull("This ref not found", thisRef);
		val thisRefTypeResult = ts.type(script.newRuleEnvironment, thisRef);
		if (thisRefTypeResult.failure) {
			assertNull("Error typing this reference", thisRefTypeResult.failureMessage)
		}
		val thisRefType = thisRefTypeResult.value;
		assertNotNull("Cannot type this reference, it's null", thisRefType);
		// we expect the normalized this type:
		assertEquals("~this[Object]", thisRefType.typeRefAsString);

		script.assertNoErrors
	}
}
