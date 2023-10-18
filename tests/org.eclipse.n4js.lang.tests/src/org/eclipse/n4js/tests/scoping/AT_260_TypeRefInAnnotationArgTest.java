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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Iterators;
import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_260_TypeRefInAnnotationArgTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;
	@Inject
	N4JSTypeSystem ts;

	@Test
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				class X { x: X; }
				@This(X) function f() {
					this.x // X.x
				}
				""");
		valTestHelper.assertNoErrors(script);
		FunctionDeclaration f = (FunctionDeclaration) script.getScriptElements().get(1);
		ParameterizedPropertyAccessExpression x = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) f
				.getBody().getStatements().get(0)).getExpression();
		assertSame(((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedTypeAsClass().getOwnedMembers()
				.get(0), x.getProperty());
	}

	@Test
	@Ignore("see IDE-496")
	public void test_02() throws Exception {
		Script script = parseHelper.parse("""
				class X { X x; }
				var X x
				@This(X) function f() {
					var nested = function() {
						this.x // global var x
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		ParameterizedPropertyAccessExpression x = Iterators.getOnlyElement(
				IteratorExtensions.filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		assertSame(((VariableStatement) script.getScriptElements().get(1)).getVarDecl().get(0), x.getProperty());
	}

	@Test
	public void test_03() throws Exception {
		Script script = parseHelper.parse("""
				class X { x: X; }
				var x: X
				function f() {
					var nested = @This(X) function() {
						this.x // member X.x
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		ParameterizedPropertyAccessExpression x = Iterators.getOnlyElement(
				IteratorExtensions.filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		assertSame(((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedTypeAsClass().getOwnedMembers()
				.get(0), x.getProperty());
	}

	@Test
	public void test_04() throws Exception {
		Script script = parseHelper.parse("""
				class Y extends X { y: Y; }
				class X {
					@This(Y)
					m(): any {
						this.y
						return null;
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_05() throws Exception {
		Script script = parseHelper.parse("""
				function f() {
					var nested = @This(~Object with{a: any;}) function() {
						this.a // member record.a
					}
				}
				""");
		N4JSResource.postProcessContainingN4JSResourceOf(script); // should make no difference, but maybe it helps to
																	// fix a Windows problem
		ThisLiteral thisRef = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ThisLiteral.class));
		assertNotNull("This ref not found", thisRef);
		TypeRef thisRefType = ts.type(newRuleEnvironment(script), thisRef);
		assertNotNull("Cannot type this reference, it's null", thisRefType);
		// we expect the normalized this type:
		assertEquals("~this[Object]", thisRefType.getTypeRefAsString());

		valTestHelper.assertNoErrors(script);
	}
}
