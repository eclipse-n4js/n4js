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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static extension org.eclipse.xtext.util.EmfFormatter.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ES_07_09_AutomaticSemicolonInsertionASTTest {

	@Inject
	extension ParseHelper<Script>

	def void isParsedAs(CharSequence input, CharSequence explicit) {
		val (EStructuralFeature)=>boolean predicate = [
			'IdentifierRef'.equals(it.EContainingClass.name)
		]
		Assert.assertEquals(explicit.parse.objToStr(predicate), input.parse.objToStr(predicate))
	}

	@Test
	def void testBinaryOperation_01() {
		'''
			1+
			2
		'''.isParsedAs('1+2;')
	}

	@Test
	def void testBinaryOperation_02() {
		'''
			1
			+2
		'''.isParsedAs('1+2;')
	}

	@Test
	def void testPostfixExpression_01() {
		'''
			1++
			2
		'''.isParsedAs('1++;2;')
	}

	@Test
	def void testPostfixExpression_02() {
		'''
			1++ +
			2
		'''.isParsedAs('1++ +2;')
	}

	@Test
	def void testPrefixExpression_01() {
		'''
			1
			++2
		'''.isParsedAs('1;++2;')
	}

	@Test
	def void testPrefixExpression_02() {
		'''
			1+
			++2
		'''.isParsedAs('1+ ++2;')
	}

	@Test
	def void testSpecExample_01() {
		'''
			{ 1
			2 } 3
		'''.isParsedAs('{1;2;} 3;')
	}

	@Test
	def void testSpecExample_02() {
		'''
			return
			1 + 2
		'''.isParsedAs('return; 1+2;')
	}

	@Test
	def void testSpecExample_03() {
		'''
			a = b
			++c
		'''.isParsedAs('a=b;++c;')
	}

	@Test
	def void testSpecExample_04() {
		'''
			b
			(1+2).print()
		'''.isParsedAs('b(1+2).print();')
	}

	@Test
	def void testSpecExample_05() {
		'''
			a = b
			/hi/g.exec(c).map(d)
		'''.isParsedAs('a=b/hi/g.exec(c).map(d);')
	}

	@Test
	def void testBlogExample_01() {
		'''
			var name = "World"
			["Hello", "Goodbye"].forEach(function(val) {
				value.print()
			}
		'''.isParsedAs('''
			var name = "World"["Hello", "Goodbye"].forEach(function(val) {
				value.print();
			};
		''')
	}

	@Test
	def void testBlogExample_02() {
		'''
			namespace.makeCounter = function() {
				var counter = 0
				return function() {
					return counter++
				}
			}
			(function() {
				namespace.exportedObject = function() {
					1+2
				}
			})()
		'''.isParsedAs('''
			namespace.makeCounter = function() {
				var counter = 0;
				return function() {
					return counter++;
				};
			}(function() {
				namespace.exportedObject = function() {
					1+2;
				};
			})();
		''')
	}

	@Test
	def void testEsprima_01() {
		'''
			{
				x
				++y
			}
		'''.isParsedAs('''
			{ x; ++y; }
		''')
	}

	@Test
	def void testEsprima_02() {
		'''
			{
				x
				--y
			}
		'''.isParsedAs('''
			{ x; --y; }
		''')
	}

	@Test
	def void testEsprima_03() {
		'''
			var x /* comment */;
		'''.isParsedAs('''
			var x;
		''')
	}

	@Test
	def void testEsprima_04() {
		'''
			{ var x = 14, y = 3
			z; }
		'''.isParsedAs('''
			{
				var x = 14, y = 3;
				z;
			}
		''')
	}

	@Test
	def void testEsprima_05() {
		'''
			while (true) { continue
			there; }
		'''.isParsedAs('''
			while (true) {
				continue;
				there;
			}
		''')
	}

	@Test
	def void testEsprima_06() {
		'''
			while (true) { continue // Comment
			there; }
		'''.isParsedAs('''
			while (true) {
				continue;
				there;
			}
		''')
	}

	@Test
	def void testEsprima_07() {
		'''
			while (true) { continue /* Multiline
			Comment */ there; }
		'''.isParsedAs('''
			while (true) {
				continue;
				there;
			}
		''')
	}

	@Test
	def void testEsprima_08() {
		'''
			while (true) { break
			there; }
		'''.isParsedAs('''
			while (true) {
				break;
				there;
			}
		''')
	}

	@Test
	def void testEsprima_09() {
		'''
			while (true) { break // Comment
			there; }
		'''.isParsedAs('''
			while (true) {
				break;
				there;
			}
		''')
	}

	@Test
	def void testEsprima_10() {
		'''
			while (true) { break /* Multiline
			Comment */ there; }
		'''.isParsedAs('''
			while (true) {
				break;
				there;
			}
		''')
	}

	@Test
	def void testEsprima_11() {
		'''
			(function(){ return
			x; })
		'''.isParsedAs('''
			(function(){
				return;
				x;
			})
		''')
	}

	@Test
	def void testEsprima_12() {
		'''
			(function(){ return//Comment
			x; })
		'''.isParsedAs('''
			(function(){
				return;
				x;
			})
		''')
	}

	@Test
	def void testEsprima_14() {
		'''
			(function(){ return/* Multiline
			Comment */ x; })
		'''.isParsedAs('''
			(function(){
				return;
				x;
			})
		''')
	}

	@Test
	def void testEsprima_15() {
		'''
			{ throw error
			error; }
		'''.isParsedAs('''
			{
				throw error;
				error;
			}
		''')
	}

	@Test
	def void testEsprima_16() {
		'''
			{ throw error// Comment
			error; }
		'''.isParsedAs('''
			{
				throw error;
				error;
			}
		''')
	}

	@Test
	def void testEsprima_17() {
		'''
			{ throw error/* Multiline
			Comment */ error; }
		'''.isParsedAs('''
			{
				throw error;
				error;
			}
		''')
	}

	@Test
	def void testEcmaVsN4JS_01() {
		'''
			var x
			y
		'''.isParsedAs('''
			var x;
			y
		''')
	}

	@Test
	def void testEcmaVsN4JS_02() {
		'''
			var x y
		'''.isParsedAs('''
			var x y;
		''')
	}

	@Test
	def void testEcmaVsN4JS_03() {
		'''
			var x /*
			*/ y
		'''.isParsedAs('''
			var x;
			y
		''')
	}

	@Test
	def void testSemiBetweenFields() {
		'''
			public class C {
				a: int = 1
				b: int = 2;
			}
		'''.isParsedAs('''
			public class C {
				a: int = 1;
				b: int = 2;
			}
		''')
	}

	@Test
	def void testSemiAfterField() {
		'''
			public class C {
				a: int = 1
			}
		'''.isParsedAs('''
			public class C {
				a: int = 1;
			}
		''')
	}
}
