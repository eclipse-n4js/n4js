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
package org.eclipse.n4js.tests.scoping;

import static com.google.common.collect.Iterators.getOnlyElement;
import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.allowClassExpressions;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.last;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Iterators;
import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_185_ThisScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;
	@Inject
	N4JSTypeSystem ts;

	@Test
	@Ignore("see IDE-496")
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				var a=1;
				function f() {
					var a=2;
					a // 2
					this.a // 1
				}
				""");
		valTestHelper.assertNoErrors(script);
		FunctionDeclaration f = (FunctionDeclaration) script.getScriptElements().get(1);
		IdentifierRef localA = (IdentifierRef) ((ExpressionStatement) f.getBody().getStatements().get(1))
				.getExpression();
		assertSame(((VariableStatement) f.getBody().getStatements().get(0)).getVarDecl().get(0), localA.getId());
		ParameterizedPropertyAccessExpression thisA = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) f
				.getBody().getStatements().get(2)).getExpression();
		assertSame(((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0), thisA.getProperty());
	}

	@Test
	@Ignore("see IDE-496")
	public void test_02() throws Exception {
		Script script = parseHelper.parse("""
				var a=1;
				function outer() {
					var a=2;
					var inner = function() {
						var a=3;
						a // 3
						this.a // 1
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		FunctionDeclaration outer = (FunctionDeclaration) script.getScriptElements().get(1);
		FunctionExpression inner = (FunctionExpression) ((VariableStatement) outer.getBody().getStatements().get(1))
				.getVarDecl().get(0).getExpression();
		IdentifierRef localA = (IdentifierRef) ((ExpressionStatement) inner.getBody().getStatements().get(1))
				.getExpression();
		assertSame(((VariableStatement) inner.getBody().getStatements().get(0)).getVarDecl().get(0), localA.getId());
		ParameterizedPropertyAccessExpression thisA = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) inner
				.getBody().getStatements().get(2)).getExpression();
		assertSame(((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0), thisA.getProperty());
	}

	@Test
	public void test_03() throws Exception {
		Script script = parseHelper.parse("""
				var a = 1
				class C {
					a: C;
					m() {
						a // outer a
						this.a // local A
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		N4ClassDeclaration clazz = (N4ClassDeclaration) last(script.getScriptElements());
		N4MethodDeclaration m = (N4MethodDeclaration) last(clazz.getOwnedMembers());
		IdentifierRef globalA = (IdentifierRef) ((ExpressionStatement) m.getBody().getStatements().get(0))
				.getExpression();
		assertSame(((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0).getDefinedVariable(),
				globalA.getId());
		ParameterizedPropertyAccessExpression thisA = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) last(
				m.getBody().getStatements())).getExpression();
		assertSame(clazz.getOwnedMembers().get(0), ((TField) thisA.getProperty()).getAstElement());
	}

	@Test
	@Ignore("see IDE-496")
	public void test_04() throws Exception {
		Script script = parseHelper.parse("""
				var a = 1
				function f() {
					this.a
				}
				""");
		valTestHelper.assertNoErrors(script);
		FunctionDeclaration f = (FunctionDeclaration) last(script.getScriptElements());
		ParameterizedPropertyAccessExpression thisA = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) last(
				f.getBody().getStatements())).getExpression();
		assertSame(((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0), thisA.getProperty());
	}

	@Test
	public void test_05() throws Exception {
		Script script = parseHelper.parse("""
				var a = 1
				function f() {
					"use strict"
					this.a
				}
				""");
		FunctionDeclaration f = (FunctionDeclaration) last(script.getScriptElements());
		ParameterizedPropertyAccessExpression thisA = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) last(
				f.getBody().getStatements())).getExpression();
		assertTrue(thisA.getProperty().eIsProxy());
	}

	@Test
	@Ignore("see IDE-496")
	public void test_06() throws Exception {
		Script script = parseHelper.parse("""
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
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_07() throws Exception {
		Script script = parseHelper.parse("""
				var o = {
					nested: {
						g: function() {
						}
					},
					callNestedG: function () {
						this.nested.g();
					},
				}
				""");
		valTestHelper.assertNoErrors(script);
		ObjectLiteral objectLiteral = (ObjectLiteral) ((VariableStatement) script.getScriptElements().get(0))
				.getVarDecl().get(0).getExpression();
		FunctionExpression callNestedG = (FunctionExpression) ((PropertyNameValuePair) last(
				objectLiteral.getPropertyAssignments())).getExpression();
		ParameterizedCallExpression callExpresion = (ParameterizedCallExpression) ((ExpressionStatement) callNestedG
				.getBody().getStatements().get(0)).getExpression();
		ParameterizedPropertyAccessExpression callTarget = (ParameterizedPropertyAccessExpression) callExpresion
				.getTarget();

		PropertyAssignment g = ((ObjectLiteral) ((PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0))
				.getExpression()).getPropertyAssignments().get(0);
		assertSame(g, ((TStructField) callTarget.getProperty()).getAstElement());
	}

	@Test
	@Ignore("see IDE-496")
	public void test_08() throws Exception {
		Script script = parseHelper.parse("""
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
				""");
		valTestHelper.assertNoErrors(script);
		ParameterizedPropertyAccessExpression thisName = getOnlyElement(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		assertSame(((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0), thisName.getProperty());
	}

	@Test
	@Ignore("enable again: this type is inferred from receiver")
	public void test_09() throws Exception {
		Script script = parseHelper.parse("""
				var x = {
					any name: ''
				}
				x.fun = function() {
					this.name
				}
				""");
		ParameterizedPropertyAccessExpression thisName = last(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		PropertyNameValuePair nameProperty = Iterators
				.getOnlyElement(filter(script.eAllContents(), PropertyNameValuePair.class));
		assertSame(nameProperty, ((SyntaxRelatedTElement) thisName.getProperty()).getAstElement());
	}

	@Test
	@Ignore("enable again: this type is inferred from receiver")
	public void test_10() throws Exception {
		Script script = parseHelper.parse("""
				var x = {
					y : {
						any name: ''
					}
				}
				x.y.fun = function() {
					this.name
				}
				""");
		ParameterizedPropertyAccessExpression thisName = last(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		Iterator<PropertyNameValuePair> nameProperty = filter(script.eAllContents(), PropertyNameValuePair.class);
		assertSame(nameProperty, ((SyntaxRelatedTElement) thisName.getProperty()).getAstElement());
	}

	@Test
	@Ignore("enable again: this type is inferred from receiver")
	public void test_11() throws Exception {
		Script script = parseHelper.parse("""
				var x = { y: function(b) {}, any fun: null }
				x.fun = function(a) {
				  if (a != 0)
				    this.y(a-1)
				}
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	@Ignore("see IDE-496")
	public void test_12() throws Exception {
		Script script = parseHelper.parse("""
				var name = 'a'
				var x = {
				    name : 'b',
				    y: {
				        name: 'c',
				        z: this.name
				    }
				}
				""");
		ParameterizedPropertyAccessExpression thisName = last(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		assertSame(((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0), thisName.getProperty());
	}

	@Test
	public void test_13() throws Exception {
		Script script = parseHelper.parse("""
				export class C<T> {
				    items_: any;
				    public indexOf(item: T): void {
				    	return _.indexOf(this.items_, item);
				    }
				}
				var _ = class _ {
					static indexOf(p1: any, p2: any): void {}
				}
				""");
		allowClassExpressions(() -> valTestHelper.assertNoErrors(script));
	}

	@Test
	public void test_14() throws Exception {
		Script script = parseHelper.parse("""
				export class C<T> {
				    items_: any;
				    public indexOf(item: T): void {
				    	return _.indexOf(this.items_, item);
				    }
				}
				class _ {
					static indexOf(p1: any, p2: any): void {}
				}
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_15() throws Exception {
		Script script = parseHelper.parse("""
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
				""");
		ParameterizedPropertyAccessExpression thisName = last(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		ObjectLiteral yLiteral = last(filter(script.eAllContents(), ObjectLiteral.class));
		PropertyNameValuePair y = (PropertyNameValuePair) yLiteral.eContainer();
		ObjectLiteral xLiteral = (ObjectLiteral) y.eContainer();
		ParameterizedTypeRefStructural xLiteralType = (ParameterizedTypeRefStructural) ts.tau(xLiteral);
		TStructField yElement = (TStructField) last(xLiteralType.getStructuralMembers());
		ParameterizedTypeRefStructural yLiteralType = (ParameterizedTypeRefStructural) yElement.getTypeRef();
		TStructMember expectation = yLiteralType.getStructuralMembers().get(0);
		assertSame(expectation, thisName.getProperty());
	}

	@Test
	public void test_16() throws Exception {
		Script script = parseHelper.parse("""
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
				""");
		ParameterizedPropertyAccessExpression thisName = last(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class)); // z: this.name;
		ObjectLiteral xLiteral = filter(script.eAllContents(), ObjectLiteral.class).next();
		ParameterizedTypeRefStructural xLiteralType = (ParameterizedTypeRefStructural) ts.tau(xLiteral);
		TStructMember expectation = xLiteralType.getStructuralMembers().get(0);
		assertSame(expectation, thisName.getProperty());
	}

}
