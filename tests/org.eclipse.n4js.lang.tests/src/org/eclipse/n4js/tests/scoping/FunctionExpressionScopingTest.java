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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class FunctionExpressionScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testScopingOfNestedFunctionExpressions() throws Exception {
		Script script = parseHelper.parse("""
				export function foo1() {
				    foo1;
				    foo1();
				    var b = function foo2() {
						foo2()
				    	var c = function foo3() {
				    		foo3()
				    		var d = function foo4() {
				    			foo4()
				    		}
				    	}
				    }
				}
				""");
		FunctionDeclaration foo1 = (FunctionDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		Assert.assertNotNull(foo1);
		List<FunctionExpression> functionExpressions = IteratorExtensions
				.toList(filter(script.eAllContents(), FunctionExpression.class));
		Assert.assertEquals(3, functionExpressions.size());
		FunctionExpression foo2 = findFirst(functionExpressions, fe -> Objects.equals(fe.getName(), "foo2"));
		Assert.assertNotNull(foo2);
		FunctionExpression foo3 = findFirst(functionExpressions, fe -> Objects.equals(fe.getName(), "foo3"));
		Assert.assertNotNull(foo3);
		FunctionExpression foo4 = findFirst(functionExpressions, fe -> Objects.equals(fe.getName(), "foo4"));
		Assert.assertNotNull(foo4);
		List<IdentifierRef> callExpressionsTargets = IteratorExtensions
				.toList(filter(script.eAllContents(), IdentifierRef.class));
		TFunction foo1CallTarget = head(filter(map(
				IterableExtensions.filter(callExpressionsTargets, cet -> Objects.equals(cet.getId().getName(), "foo1")),
				cet -> cet.getId()), TFunction.class));
		Assert.assertNotNull(foo1CallTarget);
		TFunction foo2CallTarget = head(filter(map(
				IterableExtensions.filter(callExpressionsTargets, cet -> Objects.equals(cet.getId().getName(), "foo2")),
				cet -> cet.getId()), TFunction.class));
		Assert.assertNotNull(foo2CallTarget);
		TFunction foo3CallTarget = head(filter(map(
				IterableExtensions.filter(callExpressionsTargets, cet -> Objects.equals(cet.getId().getName(), "foo3")),
				cet -> cet.getId()), TFunction.class));
		Assert.assertNotNull(foo3CallTarget);
		TFunction foo4CallTarget = head(filter(map(
				IterableExtensions.filter(callExpressionsTargets, cet -> Objects.equals(cet.getId().getName(), "foo4")),
				cet -> cet.getId()), TFunction.class));
		Assert.assertNotNull(foo4CallTarget);
		Assert.assertSame("foo1 correctly scoped", foo1CallTarget.getAstElement(), foo1);
		Assert.assertSame("foo2 correctly scoped", foo2CallTarget.getAstElement(), foo2);
		Assert.assertSame("foo3 correctly scoped", foo3CallTarget.getAstElement(), foo3);
		Assert.assertSame("foo4 correctly scoped", foo4CallTarget.getAstElement(), foo4);
	}

	@Test
	public void testShadowingOfNestedFunctions() throws Exception {
		Script script = parseHelper.parse("""
				function f1() {
				    var v = function f1() {
						f1()
				    }
				}
				""");
		valTestHelper.assertNoErrors(script);
		IdentifierRef f1 = head(filter(script.eAllContents(), IdentifierRef.class));
		TFunction fun = (TFunction) f1.getId();
		FunctionExpression expr = (FunctionExpression) fun.getAstElement();
		Assert.assertTrue(expr.eContainer().toString(), expr.eContainer() instanceof VariableDeclaration);
	}

	@Test
	public void testScopingOfNestedFunctionExpressionsComplex() throws Exception {
		Script script = parseHelper.parse("""
				var a = function foo( x ) {
					var b = function bar( y ) {
						if (y==1) { foo(2); bar(0); }
					}
					if (x==1) { b(1); }
				};
				a(1);
				""");
		List<FunctionExpression> functionExpressions = IteratorExtensions
				.toList(filter(script.eAllContents(), FunctionExpression.class));
		Assert.assertEquals(2, functionExpressions.size());
		FunctionExpression foo = findFirst(functionExpressions, fe -> Objects.equals(fe.getName(), "foo"));
		Assert.assertNotNull(foo);
		FunctionExpression bar = findFirst(functionExpressions, fe -> Objects.equals(fe.getName(), "bar"));
		Assert.assertNotNull(bar);
		List<IdentifierRef> callExpressionsTargets = IteratorExtensions
				.toList(filter(script.eAllContents(), IdentifierRef.class));
		TFunction fooCallTarget = head(filter(map(
				IterableExtensions.filter(callExpressionsTargets, cet -> Objects.equals(cet.getId().getName(), "foo")),
				cet -> cet.getId()), TFunction.class));
		Assert.assertNotNull(fooCallTarget);
		TFunction barCallTarget = head(filter(map(
				IterableExtensions.filter(callExpressionsTargets, cet -> Objects.equals(cet.getId().getName(), "bar")),
				cet -> cet.getId()), TFunction.class));
		Assert.assertNotNull(barCallTarget);
		Assert.assertSame("foo correctly scoped", fooCallTarget.getAstElement(), foo);
		Assert.assertSame("bar correctly scoped", barCallTarget.getAstElement(), bar);
	}
}
