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
package org.eclipse.n4js.tests.parser;

import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.util.EmfFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ES_07_09_AutomaticSemicolonInsertionASTTest {

	@Inject
	ParseHelper<Script> parseHelper;

	public void isParsedAs(CharSequence input, CharSequence explicit) {
		Predicate<EStructuralFeature> predicate = (f) -> "IdentifierRef".equals(f.getEContainingClass().getName());

		try {
			Assert.assertEquals(EmfFormatter.objToStr(parseHelper.parse(explicit), predicate),
					EmfFormatter.objToStr(parseHelper.parse(input), predicate));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBinaryOperation_01() {
		isParsedAs("""
				1+
				2
				""", "1+2;");
	}

	@Test
	public void testBinaryOperation_02() {
		isParsedAs("""
				1
				+2
				""", "1+2;");
	}

	@Test
	public void testPostfixExpression_01() {
		isParsedAs("""
				1++
				2
				""", "1++;2;");
	}

	@Test
	public void testPostfixExpression_02() {
		isParsedAs("""
				1++ +
				2
				""", "1++ +2;");
	}

	@Test
	public void testPrefixExpression_01() {
		isParsedAs("""
				1
				++2
				""", "1;++2;");
	}

	@Test
	public void testPrefixExpression_02() {
		isParsedAs("""
				1+
				++2
				""", "1+ ++2;");
	}

	@Test
	public void testSpecExample_01() {
		isParsedAs("""
				{ 1
				2 } 3
				""", "{1;2;} 3;");
	}

	@Test
	public void testSpecExample_02() {
		isParsedAs("""
				return
				1 + 2
				""", "return; 1+2;");
	}

	@Test
	public void testSpecExample_03() {
		isParsedAs("""
				a = b
				++c
				""", "a=b;++c;");
	}

	@Test
	public void testSpecExample_04() {
		isParsedAs("""
				b
				(1+2).print()
				""", "b(1+2).print();");
	}

	@Test
	public void testSpecExample_05() {
		isParsedAs("""
				a = b
				/hi/g.exec(c).map(d)
				""", "a=b/hi/g.exec(c).map(d);");
	}

	@Test
	public void testBlogExample_01() {
		isParsedAs("""
				var name = "World"
				["Hello", "Goodbye"].forEach(function(val) {
					value.print()
				}
				""", """
				var name = "World"["Hello", "Goodbye"].forEach(function(val) {
					value.print();
				};
				""");
	}

	@Test
	public void testBlogExample_02() {
		isParsedAs("""
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
				""", """
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
				""");
	}

	@Test
	public void testEsprima_01() {
		isParsedAs("""
				{
					x
					++y
				}
				""", """
				{ x; ++y; }
				""");
	}

	@Test
	public void testEsprima_02() {
		isParsedAs("""
				{
					x
					--y
				}
				""", """
				{ x; --y; }
				""");
	}

	@Test
	public void testEsprima_03() {
		isParsedAs("""
				var x /* comment */;
				""", """
				var x;
				""");
	}

	@Test
	public void testEsprima_04() {
		isParsedAs("""
				{ var x = 14, y = 3
				z; }
				""", """
				{
					var x = 14, y = 3;
					z;
				}
				""");
	}

	@Test
	public void testEsprima_05() {
		isParsedAs("""
				while (true) { continue
				there; }
				""", """
				while (true) {
					continue;
					there;
				}
				""");
	}

	@Test
	public void testEsprima_06() {
		isParsedAs("""
				while (true) { continue // Comment
				there; }
				""", """
				while (true) {
					continue;
					there;
				}
				""");
	}

	@Test
	public void testEsprima_07() {
		isParsedAs("""
				while (true) { continue /* Multiline
				Comment */ there; }
				""", """
				while (true) {
					continue;
					there;
				}
				""");
	}

	@Test
	public void testEsprima_08() {
		isParsedAs("""
				while (true) { break
				there; }
				""", """
				while (true) {
					break;
					there;
				}
				""");
	}

	@Test
	public void testEsprima_09() {
		isParsedAs("""
				while (true) { break // Comment
				there; }
				""", """
				while (true) {
					break;
					there;
				}
				""");
	}

	@Test
	public void testEsprima_10() {
		isParsedAs("""
				while (true) { break /* Multiline
				Comment */ there; }
				""", """
				while (true) {
					break;
					there;
				}
				""");
	}

	@Test
	public void testEsprima_11() {
		isParsedAs("""
				(function(){ return
				x; })
				""", """
				(function(){
					return;
					x;
				})
				""");
	}

	@Test
	public void testEsprima_12() {
		isParsedAs("""
				(function(){ return//Comment
				x; })
				""", """
				(function(){
					return;
					x;
				})
				""");
	}

	@Test
	public void testEsprima_14() {
		isParsedAs("""
				(function(){ return/* Multiline
				Comment */ x; })
				""", """
				(function(){
					return;
					x;
				})
				""");
	}

	@Test
	public void testEsprima_15() {
		isParsedAs("""
				{ throw error
				error; }
				""", """
				{
					throw error;
					error;
				}
				""");
	}

	@Test
	public void testEsprima_16() {
		isParsedAs("""
				{ throw error// Comment
				error; }
				""", """
				{
					throw error;
					error;
				}
				""");
	}

	@Test
	public void testEsprima_17() {
		isParsedAs("""
				{ throw error/* Multiline
				Comment */ error; }
				""", """
				{
					throw error;
					error;
				}
				""");
	}

	@Test
	public void testEcmaVsN4JS_01() {
		isParsedAs("""
				var x
				y
				""", """
				var x;
				y
				""");
	}

	@Test
	public void testEcmaVsN4JS_02() {
		isParsedAs("""
				var x y
				""", """
				var x y;
				""");
	}

	@Test
	public void testEcmaVsN4JS_03() {
		isParsedAs("""
				var x /*
				*/ y
				""", """
				var x;
				y
				""");
	}

	@Test
	public void testSemiBetweenFields() {
		isParsedAs("""
				public class C {
					a: int = 1
					b: int = 2;
				}
				""", """
				public class C {
					a: int = 1;
					b: int = 2;
				}
				""");
	}

	@Test
	public void testSemiAfterField() {
		isParsedAs("""
				public class C {
					a: int = 1
				}
				""", """
				public class C {
					a: int = 1;
				}
				""");
	}
}
