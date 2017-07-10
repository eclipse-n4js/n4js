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
package org.eclipse.n4js.transpiler.es.tests

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.transpiler.utils.TranspilerUtils
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.StringLiteral

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TranspilerUtilsTest extends AbstractTranspilerTest {


	@Test
	def void testEmptyBlock() {
		val  script = '''function a (){}'''.createScript
		script.resolveLazyRefs

		val f1 = script.scriptElements.get(0) as FunctionDeclaration;
		assertEquals( "a", f1.name )

		val collected = TranspilerUtils.collectNodesWithinSameThisEnvironment(f1, EObject );
	 	assertEquals(1, collected.size)  // gets the empty Block element.
		assertTrue( collected.get(0) instanceof Block)
	}

	@Test
	def void testSimpleBlock() {
		val  script = '''function a (){
			var boolean b = true;
			if( b ) { return "B" }
			else { return "C" }

			if( b ) {
				{
					return "D";
				}
			}

			return "A";
		}'''.createScript
		script.resolveLazyRefs

		val f1 = script.scriptElements.get(0) as FunctionDeclaration;
		assertEquals( "a", f1.name )

		val collected = TranspilerUtils.collectNodesWithinSameThisEnvironment(f1, ReturnStatement );
	 	assertEquals(4, collected.size)

	 	val sorted = collected.map[expression].filter(StringLiteral).map[value].sort
	 	val expected = #["A","B","C","D"];

	 	assertArrayEquals(expected,sorted)


	}


		@Test
	def void testNestedBlock() {
		val  script = '''function a (){
			var boolean b = true;
			if( b ) { return "B" }
			else { return "C" }

			if( b ) {
				{
					return "D";
				}
			}

			var x = function() {
				var z = function z () { return "G"; }
				return "E";
			}

			var y = ()=>{ return "F"; }

			switch(5) {
				case 4: return "H";
			}

			var z1 = () => {   return "I"; 	}
			var z2 = async () => { 	return "J"; 	}
			var z2 = async () => { 	return await "J"; 	}

			return "A";
		}'''.createScript
		script.resolveLazyRefs

		val f1 = script.scriptElements.get(0) as FunctionDeclaration;
		assertEquals( "a", f1.name )

		val collected = TranspilerUtils.collectNodesWithinSameThisEnvironment(f1, ReturnStatement );

	 	val sorted = collected.map[expression].filter(StringLiteral).map[value].sort
	 	val expected = #["A","B","C","D","H"];

	 	assertArrayEquals(expected,sorted)


	}


}
