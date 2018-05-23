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

import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.junit.Test

class ES_07_04_CommentParserEcmaTest extends AbstractParserTest {

	@Test
	def void testDoubleClosedComment() {
		val script = '/* var */ x*/'.parseWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val multi = statement.expression as MultiplicativeExpression
		assertEquals('x', (multi.lhs as IdentifierRef).text)
		val regex = multi.rhs as RegularExpressionLiteral
		assertEquals('/', regex.value)
	}
}
