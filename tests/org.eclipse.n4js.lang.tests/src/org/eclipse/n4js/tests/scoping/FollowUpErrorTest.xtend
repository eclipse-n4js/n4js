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

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class FollowUpErrorTest {

	@Inject extension ParseHelper<Script>

	@Test
	def void testNoErrorOnDynamicType() {
		val script = '''
			var s: String+ = null
			s.doesNotExist
		'''.parse
		val c = script.scriptElements.last as ExpressionStatement
		val p = c.expression as ParameterizedPropertyAccessExpression
		Assert.assertTrue(p.property.eIsProxy)
		Assert.assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testNoErrorOnUnknownType() {
		val script = '''
			var u: UnknownType = null
			u.doesNotExist
		'''.parse
		val c = script.scriptElements.last as ExpressionStatement
		val p = c.expression as ParameterizedPropertyAccessExpression
		Assert.assertTrue(p.property.eIsProxy)
		Assert.assertEquals(script.eResource.errors.toString(), 1, script.eResource.errors.size)
		Assert.assertEquals("Couldn't resolve reference to Type 'UnknownType'.", script.eResource.errors.head.message)
	}

	@Test
	def void testNoErrorAfterUnresolvableProperty() {
		val script = '''
			var s: String = null
			s.doesNotExist.either
		'''.parse
		val c = script.scriptElements.last as ExpressionStatement
		val p = c.expression as ParameterizedPropertyAccessExpression
		Assert.assertTrue(p.property.eIsProxy)
		Assert.assertEquals(1, script.eResource.errors.size)
		Assert.assertEquals("Couldn't resolve reference to IdentifiableElement 'doesNotExist'.", script.eResource.errors.head.message)
	}

	@Test
	def void testNoErrorAfterUnresolvableInferredType() {
		val script = '''
			var u = doesNotExist
			u.either
		'''.parse
		val c = script.scriptElements.last as ExpressionStatement
		val p = c.expression as ParameterizedPropertyAccessExpression
		Assert.assertTrue(p.property.eIsProxy)
		Assert.assertEquals(1, script.eResource.errors.size)
		Assert.assertEquals("Couldn't resolve reference to IdentifiableElement 'doesNotExist'.", script.eResource.errors.head.message)
	}

	@Test
	def void testErrorOnStaticType() {
		val script = '''
			var s: String = null
			s.doesNotExist
		'''.parse
		val c = script.scriptElements.last as ExpressionStatement
		val p = c.expression as ParameterizedPropertyAccessExpression
		Assert.assertTrue(p.property.eIsProxy)
		Assert.assertEquals(script.eResource.errors.toString(), 1, script.eResource.errors.size)
		Assert.assertEquals("Couldn't resolve reference to IdentifiableElement 'doesNotExist'.", script.eResource.errors.head.message)
	}
}
