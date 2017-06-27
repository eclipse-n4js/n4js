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

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(N4JSInjectorProvider))
class ES_07_09_SemicolonInjectionTest {

	@Inject
	extension ParseHelper<Script>

	@Test
	def void testFunctionWithDeadCode() {
		val script = '''
			var c = function() {
				return
				1
			}
		'''.parse
		val secondStatement = script.eAllContents.findFirst[
			it instanceof NumericLiteral
		] as NumericLiteral
		val statement = secondStatement.eContainer as ExpressionStatement
		val block = statement.eContainer as Block
		Assert.assertEquals(2, block.statements.size)
	}

}
