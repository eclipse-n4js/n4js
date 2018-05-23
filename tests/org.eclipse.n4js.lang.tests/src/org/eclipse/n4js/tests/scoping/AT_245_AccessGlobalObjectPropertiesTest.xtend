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
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_245_AccessGlobalObjectPropertiesTest {

	@Inject extension N4JSParseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void test_01() {
		val script = '''
			this.undefined
		'''.parse(JavaScriptVariant.unrestricted)
		script.assertNoErrors
		val expression = (script.scriptElements.head as ExpressionStatement).expression as ParameterizedPropertyAccessExpression
		val property = expression.property as TField
		Assert.assertEquals("GlobalObject", (property.eContainer as TClass).name)
		Assert.assertEquals("undefined", (property.typeRef as ParameterizedTypeRef).declaredType.name)
	}

	@Test
	def void test_02() {
		val script = '''
			undefined
		'''.parse(JavaScriptVariant.unrestricted)
		script.assertNoErrors
		val expression = (script.scriptElements.head as ExpressionStatement).expression as IdentifierRef
		val property = expression.id as TField
		Assert.assertEquals("GlobalObject", (property.eContainer as TClass).name)
		Assert.assertEquals("undefined", (property.typeRef as ParameterizedTypeRef).declaredType.name)
	}

}
