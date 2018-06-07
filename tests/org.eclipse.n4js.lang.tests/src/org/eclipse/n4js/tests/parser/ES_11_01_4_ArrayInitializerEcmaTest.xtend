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
package org.eclipse.n4js.tests.parser

import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.junit.Test

class ES_11_01_4_ArrayInitializerEcmaTest extends AbstractParserTest {

	@Test
	def void testArrayInitializer_01() {
		val script = 'x = [,,]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(2, array.elements.size)
	}

}
