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
package org.eclipse.n4js.tests.n4JS.extensions

import java.util.List
import javax.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableEnvironmentElement
import org.eclipse.n4js.scoping.utils.SourceElementExtensions
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
@RunWith(XtextRunner)
class SourceElementExtensionsTest {
	@Inject private extension ParseHelper<Script>
	@Inject private extension ValidationTestHelper;

	@Inject extension SourceElementExtensions

	@Test
	def testCollectVisibleIdentifiableElementsForFunctionExpression() {
		val script = '''
		var _ = 2
		var a = function fun1() {
			var x = 1;
			var b = function fun2() {
				var y = 2;
			}
		}
		'''.parse
		assertTrue(script.validate.empty)

		//use of '_' discouraged : http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.27.1

		val fun1 = script.eAllContents.filter(FunctionExpression).filter[name=="fun1"].head
		val fun2 = script.eAllContents.filter(FunctionExpression).filter[name=="fun2"].head

		assertEquals("_, a", script.toVisibleElementNameList)
		assertEquals("fun1, x, b, arguments", fun1.toVisibleElementNameList)
		assertEquals("fun2, y, arguments", fun2.toVisibleElementNameList)
	}

	def testCollectVisibleIdentifiableElements_Var_vs_Let() {
		val script = '''
			var v0;
			let l0;

			if(true) {
				var v1;
				let l1;
			}
		'''.parse;
		assertTrue(script.validate.empty)

		val block = script.eAllContents.filter(IfStatement).head.ifStmt as Block;
		assertNotNull(block);

		assertEquals("v0, l0, v1", script.toVisibleElementNameList);
		assertEquals("l1", block.toVisibleElementNameList);
	}

	def private toVisibleElementNameList(VariableEnvironmentElement element) {
		collectVisibleIdentifiableElements(element).toNameList
	}

	def private toNameList(List<IdentifiableElement> visibleElements) {
		visibleElements.map[name].join(", ")
	}
}
