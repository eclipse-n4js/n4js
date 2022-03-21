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
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TStructField
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.*
import static org.junit.Assert.*

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_185_ThisScopingTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper
	@Inject N4JSTypeSystem ts

	@Test
	@Ignore("see IDE-496")
	def void test_01() {
		val script = '''
			var a=1;
			function f() {
				var a=2;
				a // 2
				this.a // 1
			}
		'''.parse
		script.assertNoErrors
		val f = script.scriptElements.get(1) as FunctionDeclaration
		val localA = (f.body.statements.get(1) as ExpressionStatement).expression as IdentifierRef
		assertSame((f.body.statements.head as VariableStatement).varDecl.head, localA.id)
		val thisA = (f.body.statements.get(2) as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		assertSame((script.scriptElements.head as VariableStatement).varDecl.head, thisA.property)
	}

	@Test
	@Ignore("see IDE-496")
	def void test_02() {
		val script = '''
			var a=1;
			function outer() {
				var a=2;
				var inner = function() {
					var a=3;
					a // 3
					this.a // 1
				}
			}
		'''.parse
		script.assertNoErrors
		val outer = script.scriptElements.get(1) as FunctionDeclaration
		val inner = (outer.body.statements.get(1) as VariableStatement).varDecl.head.expression as FunctionExpression
		val localA = (inner.body.statements.get(1) as ExpressionStatement).expression as IdentifierRef
		assertSame((inner.body.statements.head as VariableStatement).varDecl.head, localA.id)
		val thisA = (inner.body.statements.get(2) as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		assertSame((script.scriptElements.head as VariableStatement).varDecl.head, thisA.property)
	}

	@Test
	def void test_03() {
		val script = '''
			var a = 1
			class C {
				a: C;
				m() {
					a // outer a
					this.a // local A
				}
			}
		'''.parse
		script.assertNoErrors
		val clazz = script.scriptElements.last as N4ClassDeclaration
		val m = clazz.ownedMembers.last as N4MethodDeclaration
		val globalA = (m.body.statements.head as ExpressionStatement).expression as IdentifierRef
		assertSame((script.scriptElements.head as VariableStatement).varDecl.head.definedVariable, globalA.id)
		val thisA = (m.body.statements.last as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		assertSame(clazz.ownedMembers.head as N4FieldDeclaration, (thisA.property as TField).astElement)
	}

	@Test
	@Ignore("see IDE-496")
	def void test_04() {
		val script = '''
			var a = 1
			function f() {
				this.a
			}
		'''.parse
		script.assertNoErrors
		val f = script.scriptElements.last as FunctionDeclaration
		val thisA = (f.body.statements.last as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		assertSame((script.scriptElements.head as VariableStatement).varDecl.head, thisA.property)
	}

	@Test
	def void test_05() {
		val script = '''
			var a = 1
			function f() {
				"use strict"
				this.a
			}
		'''.parse
		val f = script.scriptElements.last as FunctionDeclaration
		val thisA = (f.body.statements.last as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		assertTrue(thisA.property.eIsProxy)
	}

	@Test
	@Ignore("see IDE-496")
	def void test_06() {
		val script = '''
			function f1() {
				this
			}
			function f2() {
				"use strict"
				this
			}
			var name: any = 'global'
			var o = {
				any name: "o",
				f: function() {
					this.name
				},
				nested: {
					any name: "nested",
					g: function() {
						this.name
					}
				},
				callNestedG: function () { this.nested.g(); },
				callf1: function() { f1(); },
				callf2: function() { f2(); },
				complex: function() {
					var name = "complex";
					function h() {
					    this.name
					}
					h();
					this.name
				}
			}
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_07() {
		val script = '''
			var o = {
				nested: {
					g: function() {
					}
				},
				callNestedG: function () {
					this.nested.g();
				},
			}
		'''.parse
		script.assertNoErrors
		val objectLiteral = (script.scriptElements.head as VariableStatement).varDecl.head.expression as ObjectLiteral
		val callNestedG = (objectLiteral.propertyAssignments.last as PropertyNameValuePair).expression as FunctionExpression
		val callExpresion= (callNestedG.body.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		val callTarget = callExpresion.target as ParameterizedPropertyAccessExpression

		val g = ((objectLiteral.propertyAssignments.head as PropertyNameValuePair).expression as ObjectLiteral).propertyAssignments.head
		assertSame(g, (callTarget.property as TStructField).astElement)
	}

	@Test
	@Ignore("see IDE-496")
	def void test_08() {
		val script = '''
			var name: any = 'global'
			var o = {
				any name: "o",
				complex: function() {
					var name = "complex";
					function h() {
					    this.name
					}
				}
			}
		'''.parse
		script.assertNoErrors
		val thisName = Iterators.getOnlyElement(script.eAllContents.filter(ParameterizedPropertyAccessExpression))
		assertSame((script.scriptElements.head as VariableStatement).varDecl.head, thisName.property)
	}

	@Test
	@Ignore("enable again: this type is inferred from receiver")
	def void test_09() {
		val script = '''
			var x = {
				any name: ''
			}
			x.fun = function() {
				this.name
			}
		'''.parse
		val thisName = script.eAllContents.filter(ParameterizedPropertyAccessExpression).last
		val nameProperty = Iterators.getOnlyElement(script.eAllContents.filter(PropertyNameValuePair))
		assertSame(nameProperty, (thisName.property as SyntaxRelatedTElement).astElement)
	}

	@Test
	@Ignore("enable again: this type is inferred from receiver")
	def void test_10() {
		val script = '''
			var x = {
				y : {
					any name: ''
				}
			}
			x.y.fun = function() {
				this.name
			}
		'''.parse
		val thisName = script.eAllContents.filter(ParameterizedPropertyAccessExpression).last
		val nameProperty = script.eAllContents.filter(PropertyNameValuePair).last
		assertSame(nameProperty, (thisName.property as SyntaxRelatedTElement).astElement)
	}

	@Test
	@Ignore("enable again: this type is inferred from receiver")
	def void test_11() {
		val script = '''
			var x = { y: function(b) {}, any fun: null }
			x.fun = function(a) {
			  if (a != 0)
			    this.y(a-1)
			}
		'''.parse
		script.assertNoErrors
	}

	@Test
	@Ignore("see IDE-496")
	def void test_12() {
		val script = '''
			var name = 'a'
			var x = {
			    name : 'b',
			    y: {
			        name: 'c',
			        z: this.name
			    }
			}
		'''.parse
		val thisName = script.eAllContents.filter(ParameterizedPropertyAccessExpression).last
		assertSame((script.scriptElements.head as VariableStatement).varDecl.head, thisName.property)
	}

	@Test
	def void test_13() {
		val script = '''
			export class C<T> {
			    items_: any;
			    public indexOf(item: T): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			var _ = class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.parse
allowClassExpressions[
		script.assertNoErrors
]
	}

	@Test
	def void test_14() {
		val script = '''
			export class C<T> {
			    items_: any;
			    public indexOf(item: T): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_15() {
		val script = '''
			var name = 'a'
			var x = {
			    name : 'b',
			    y: {
			        name: 'c',
			        get z() {
			        	return this.name
			        }
			    }
			}
		'''.parse
		val thisName = script.eAllContents.filter(ParameterizedPropertyAccessExpression).last
		val yLiteral = script.eAllContents.filter(ObjectLiteral).last
		val y = yLiteral.eContainer as PropertyNameValuePair
		val xLiteral = y.eContainer as ObjectLiteral
		val xLiteralType = ts.tau(xLiteral) as ParameterizedTypeRefStructural
		val yElement = xLiteralType.structuralMembers.last as TStructField
		val yLiteralType = yElement.typeRef as ParameterizedTypeRefStructural
		val expectation = yLiteralType.structuralMembers.head
		assertSame(expectation, thisName.property)
	}

	@Test
	def void test_16() {
		val script = '''
			var name = 'a'
			var x = {
			    name : 'b',
			    get y(): string {
			    	return {
				        name: 'c',
				        z: this.name
				    }.name
			    }
			}
		'''.parse
		val thisName = script.eAllContents.filter(ParameterizedPropertyAccessExpression).last // z: this.name
		val xLiteral = script.eAllContents.filter(ObjectLiteral).head
		val xLiteralType = ts.tau(xLiteral) as ParameterizedTypeRefStructural
		val expectation = xLiteralType.structuralMembers.head
		assertSame(expectation, thisName.property)
	}

}
