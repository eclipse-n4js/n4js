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

import org.junit.Test
import org.eclipse.n4js.n4JS.FunctionExpression

/**
 */
class IDEBUG_085_Test extends AbstractParserTest {

	@Test
	def void testNoFunctionExpressions_01() {
		val script = '''
			function lenFunctionDeclaration(s: string) : number {
				return s.length;
			}

			function wrap(){
				@This(any)
				function lenAnnotatedFunctionDeclarationYYY(s: string) : number {
					return s.length;
				}

				function lenAnnotatedFunctionDeclarationXXX(s: string) : number {
					return s.length;
				}
			}

			@This(any)
			function lenAnnotatedScriptElement(s: string) : number {
				return s.length;
			}

			export function lenExportedFunctionDeclaration(s: string) : number {
				return s.length;
			}

			@This(any)
			export function lenAnnotatedExportableElement(s: string) : number {
				return s.length;
			}
		'''.parseSuccessfully
		val expressions = script.eAllContents.filter[ it instanceof FunctionExpression ].toList
		assertTrue(expressions.toString, expressions.isEmpty)
	}

	@Test
	def void testNoFunctionExpressions_02() {
		val script = '''
			function f(){
				@This(any)
				function g() {}
			}
		'''.parseSuccessfully
		val expressions = script.eAllContents.filter[ it instanceof FunctionExpression ].toList
		assertTrue(expressions.toString, expressions.isEmpty)
	}


}
