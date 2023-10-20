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

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class ExpressionScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testInvocationOnThis() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					recursive(): C {
						this.recursive().recursive
					}
				}
				""");
		N4ClassDeclaration c = (N4ClassDeclaration) script.getScriptElements().get(0);
		N4MethodDeclaration recursive = (N4MethodDeclaration) c.getOwnedMembers().get(0);
		ExpressionStatement statement = (ExpressionStatement) recursive.getBody().getStatements().get(0);
		ParameterizedPropertyAccessExpression expressionPath = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		Assert.assertSame(recursive, ((TMember) expressionPath.getProperty()).getAstElement());
	}

	@Test
	public void testInvocationOnThisWithInheritance() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					m1(): D {}
				}
				public class D extends C {
					m2(): C {
						this.m1
					}
				}
				""");
		N4ClassDeclaration c = (N4ClassDeclaration) script.getScriptElements().get(0);
		N4ClassDeclaration d = (N4ClassDeclaration) IterableExtensions.last(script.getScriptElements());
		N4MethodDeclaration m2 = (N4MethodDeclaration) d.getOwnedMembers().get(0);
		ExpressionStatement statement = (ExpressionStatement) m2.getBody().getStatements().get(0);
		ParameterizedPropertyAccessExpression expressionPath = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		Assert.assertSame(c.getOwnedMembers().get(0), ((TMember) expressionPath.getProperty()).getAstElement());
	}

	@Test
	public void testFunctionDeclarationDefinedIdentifiable() throws Exception {
		Script script = parseHelper.parse("""
				function a() {}
				a = null
				""");
		FunctionDeclaration a = (FunctionDeclaration) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) ((ExpressionStatement) IterableExtensions
				.last(script.getScriptElements())).getExpression();
		IdentifierRef lhs = (IdentifierRef) assignment.getLhs();
		Assert.assertSame(a.getDefinedType(), lhs.getId());
	}

	@Test
	public void testInstanceof() throws Exception {
		Script script = parseHelper.parse("""
				function a() {}
				if (null instanceof a) {}
				""");
		FunctionDeclaration a = (FunctionDeclaration) script.getScriptElements().get(0);
		RelationalExpression cond = (RelationalExpression) ((IfStatement) IterableExtensions
				.last(script.getScriptElements())).getExpression();
		IdentifierRef type = (IdentifierRef) cond.getRhs();
		Assert.assertSame(a.getDefinedType(), type.getId());
	}

	@Test
	public void testNew_01() throws Exception {
		Script script = parseHelper.parse("""
				function a() {}
				new a()
				""");
		FunctionDeclaration a = (FunctionDeclaration) script.getScriptElements().get(0);
		NewExpression newExpr = (NewExpression) ((ExpressionStatement) IterableExtensions
				.last(script.getScriptElements())).getExpression();
		IdentifierRef type = (IdentifierRef) newExpr.getCallee();
		Assert.assertSame(a.getDefinedType(), type.getId());
	}

	@Test
	public void testNew_02() throws Exception {
		Script script = parseHelper.parse("""
				var a = function() {}
				new a()
				""");
		VariableDeclaration a = ((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0);
		NewExpression newExpr = (NewExpression) ((ExpressionStatement) IterableExtensions
				.last(script.getScriptElements())).getExpression();
		IdentifierRef type = (IdentifierRef) newExpr.getCallee();
		Assert.assertSame(a.getDefinedVariable(), type.getId());
	}

}
