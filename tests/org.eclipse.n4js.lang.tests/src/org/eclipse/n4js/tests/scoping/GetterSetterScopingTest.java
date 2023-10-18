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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.members.MemberScope;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for this scoping, combined with type system test.
 *
 * <p>
 * Note: In this test, the this literal needs to be followed by a property access in order to force the scoping to
 * actually create the type. That is, we use "this.s" instead of "this".
 * </p>
 *
 * @see AT_185_ThisScopingTest
 * @see ThisScopingTest
 * @see MemberScope
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class GetterSetterScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	/**
	 * Referenced property (or getter/setter) must be called "target"
	 */
	void assertPropertyBinding(CharSequence scriptSrc, boolean readable, boolean expectError) {
		try {
			Script script = parseHelper.parse(scriptSrc +
					(readable ? "ol.target;" : "ol.target=null;"));
			valTestHelper.validate(script);

			// val G = newRuleEnvironment(script);
			Expression expr = ((ExpressionStatement) last(script.getScriptElements())).getExpression();

			ObjectLiteral objectLiteral = head(EcoreUtil2.eAllOfType(script, ObjectLiteral.class));
			TStructuralType objectLiteralStructuralType = (TStructuralType) objectLiteral.getDefinedType();
			boolean staticAccess = false;
			TMember member = objectLiteralStructuralType.findOwnedMember("target", !readable, staticAccess);
			if (member == null) {
				member = objectLiteralStructuralType.findOwnedMember("target", readable, staticAccess);
			}

			ParameterizedPropertyAccessExpression operand = readable ? (ParameterizedPropertyAccessExpression) expr
					: (ParameterizedPropertyAccessExpression) ((AssignmentExpression) expr).getLhs();

			assertSame(member, operand.getProperty());
			if (!expectError) {
				valTestHelper.assertNoErrors(script);
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testReadProperty() {
		assertPropertyBinding(
				"""
						var ol = {
							target: "hello"
						}
						""", true, false);
	}

	@Test
	public void testReferToGetter() {
		assertPropertyBinding(
				"""
						var ol = {
							get target() {return null}
						}
						""", true, false);
	}

	@Test
	public void testReferToSetter() {
		assertPropertyBinding(
				"""
						var ol = {
							set target(x) {}
						}
						""", false, false);
	}

	@Test
	public void testWriteProperty() {
		assertPropertyBinding(
				"""
						var ol = {
							target: "hello"
						}
						""", true, false);
	}

	@Test
	public void testWronglyReferToGetter() {
		assertPropertyBinding(
				"""
						var ol = {
							get target() {return null}
						}
						""", false, true);
	}

	@Test
	public void testWronglyReferToSetter() {
		assertPropertyBinding(
				"""
						var ol = {
							set target(x) {}
						}
						""", true, true);
	}
}
