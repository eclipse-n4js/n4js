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
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
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
public class FollowUpErrorTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testNoErrorOnDynamicType() throws Exception {
		Script script = parseHelper.parse("""
				var s: String+ = null
				s.doesNotExist
				""");
		ExpressionStatement c = (ExpressionStatement) IterableExtensions.last(script.getScriptElements());
		ParameterizedPropertyAccessExpression p = (ParameterizedPropertyAccessExpression) c.getExpression();
		Assert.assertTrue(p.getProperty().eIsProxy());
		Assert.assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testNoErrorOnUnknownType() throws Exception {
		Script script = parseHelper.parse("""
				var u: UnknownType = null
				u.doesNotExist
				""");
		ExpressionStatement c = (ExpressionStatement) IterableExtensions.last(script.getScriptElements());
		ParameterizedPropertyAccessExpression p = (ParameterizedPropertyAccessExpression) c.getExpression();
		Assert.assertTrue(p.getProperty().eIsProxy());
		Assert.assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Assert.assertEquals("Couldn't resolve reference to Type 'UnknownType'.",
				script.eResource().getErrors().get(0).getMessage());
	}

	@Test
	public void testNoErrorAfterUnresolvableProperty() throws Exception {
		Script script = parseHelper.parse("""
				var s: String = null
				s.doesNotExist.either
				""");
		ExpressionStatement c = (ExpressionStatement) IterableExtensions.last(script.getScriptElements());
		ParameterizedPropertyAccessExpression p = (ParameterizedPropertyAccessExpression) c.getExpression();
		Assert.assertTrue(p.getProperty().eIsProxy());
		Assert.assertEquals(1, script.eResource().getErrors().size());
		Assert.assertEquals("Couldn't resolve reference to IdentifiableElement 'doesNotExist'.",
				script.eResource().getErrors().get(0).getMessage());
	}

	@Test
	public void testNoErrorAfterUnresolvableInferredType() throws Exception {
		Script script = parseHelper.parse("""
				var u = doesNotExist
				u.either
				""");
		ExpressionStatement c = (ExpressionStatement) IterableExtensions.last(script.getScriptElements());
		ParameterizedPropertyAccessExpression p = (ParameterizedPropertyAccessExpression) c.getExpression();
		Assert.assertTrue(p.getProperty().eIsProxy());
		Assert.assertEquals(1, script.eResource().getErrors().size());
		Assert.assertEquals("Couldn't resolve reference to IdentifiableElement 'doesNotExist'.",
				script.eResource().getErrors().get(0).getMessage());
	}

	@Test
	public void testErrorOnStaticType() throws Exception {
		Script script = parseHelper.parse("""
				var s: String = null
				s.doesNotExist
				""");
		ExpressionStatement c = (ExpressionStatement) IterableExtensions.last(script.getScriptElements());
		ParameterizedPropertyAccessExpression p = (ParameterizedPropertyAccessExpression) c.getExpression();
		Assert.assertTrue(p.getProperty().eIsProxy());
		Assert.assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Assert.assertEquals("Couldn't resolve reference to IdentifiableElement 'doesNotExist'.",
				script.eResource().getErrors().get(0).getMessage());
	}
}
