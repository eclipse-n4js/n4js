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
package org.eclipse.n4js.tests.n4JS;

import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.allowClassExpressions;
import static org.eclipse.xtext.EcoreUtil2.eAllOfType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThisTarget;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for computing probable this target.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class ProbableThisTargetTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	public void assertThisTarget(CharSequence scriptSrc, boolean expectedErrors) {
		Script script;
		try {
			script = parseHelper.parse(scriptSrc);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return;
		}
		if (!expectedErrors) {
			valTestHelper.assertNoErrors(script);
		} else {
			valTestHelper.validate(script);
		}

		EObject targetElement = findFirst(eAllOfType(script, VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "target"));
		if (targetElement == null) {
			targetElement = findFirst(eAllOfType(script, N4ClassifierDeclaration.class),
					cd -> Objects.equals(cd.getName(), "target"));
		}
		EObject thisTarget = (targetElement == null) ? targetElement
				: eAllOfType(targetElement, ThisTarget.class).get(0);

		for (ThisLiteral thisLiteral : eAllOfType(script, ThisLiteral.class)) {
			EObject containingThisTarget = N4JSASTUtils.getProbableThisTarget(thisLiteral);
			assertSame(thisTarget, containingThisTarget);
		}
	}

	@Test
	public void testObjectLiteralInFunction() {
		assertThisTarget(
				"""
							var target = {
								s: "hello",
								f: function() {
									this;
								}
							}
						""", false);
	}

	@Test
	public void testObjectLiteralInGetter() {
		assertThisTarget(
				"""
							var target = {
								s: "hello",
								get x() {
									this;
									return null;
								}
							}
						""", false);
	}

	@Test
	public void testObjectLiteralInSetter() {
		assertThisTarget(
				"""
							var target = {
								s: "hello",
								set x(y) {
									this;
								}
							}
						""", false);
	}

	@Test
	public void testObjectLiteralNotInValue() {
		assertThisTarget(
				"""
							var notarget = {
								s: this
							}
						""", false);
	}

	@Test
	public void testN4ClassInMethod() {
		allowClassExpressions(() -> assertThisTarget(
				"""
							var target = class {
								f(): void {
									this;
								}
							}
						""", false));
	}

	@Test
	public void testN4ClassDeclInMethod() {
		assertThisTarget(
				"""
							class target {
								f(): void {
									this;
								}
							}
						""", false);
	}

	@Test
	public void testN4InterfaceDeclInMethod() {
		assertThisTarget(
				"""
							interface target {
								f(): void {
									this;
								}
							}
						""", false);
	}

	@Test
	public void testN4ClassInGetter() {
		allowClassExpressions(() -> assertThisTarget(
				"""
							var target = class {
								get x() {
									this;
									return null;
								}
							}
						""", false));
	}

	@Test
	public void testN4ClassInSetter() {
		allowClassExpressions(() -> assertThisTarget(
				"""
							var target = class {
								set x(y) {
									this;
								}
							}
						""", false));
	}

	@Test
	public void testN4ClassInField() {
		allowClassExpressions(() -> assertThisTarget(
				"""
							var target = class {
								x = this;
							}
						""", false));
	}

	@Test
	public void testNotInNestedFunction() {
		assertThisTarget(
				"""
							var notarget = {
								s: "hello",
								f: function() {
									var x = function() {
										this;
									}
								}
							}
						""", false);
	}

	@Test
	public void testNotInFunctionExpression() {
		assertThisTarget(
				"""
							var notarget = function() {
								this;
							}
						""", false);
	}

	@Test
	public void testNotInFunctionDeclaration() {
		assertThisTarget(
				"""
							function notarget(){
								this;
							}
						""", false);
	}

}
