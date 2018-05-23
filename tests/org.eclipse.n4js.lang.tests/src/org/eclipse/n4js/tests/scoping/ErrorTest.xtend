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
import com.google.inject.Provider
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.analysis.ExceptionAnalyser
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Extracted cases from smoke tests (see Smoketests.xtend)
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ErrorTest extends AbstractParserTest {

	@Inject
	ExceptionAnalyser analyser

	@Inject protected Provider<XtextResourceSet> resourceSetProvider

	@Test
	def void testNoStackoverflow_01() {
		val script = '''
			var a = a || {};
			a.b
		'''.parse(URI.createURI('sample.n4js'), resourceSetProvider.get)
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoStackoverflow_02() {
		val script = '''
			var u =
			u.either
		'''.parse(URI.createURI('sample.n4js'), resourceSetProvider.get)
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoNPE_01() {
		val script = '''
			class C<T> {
			    boolean b = null
			    x() {
				    spec
			    }
			    y(number? n) {
			    }
			}
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoNPE_02() {
		val script = '''
			class C<T> {
			    m(? p) {
			    }
			    n() {
			        this.m();
			    }
			}
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoNPE_03() {
		val script = '''
			var target = {
				s: "hello",
				set x
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoNPE_04() {
		val script = '''
			class C {}
			function f( ~C with {
				s: string;
				n;
				f1(string)
				void f2()
				number f3(boolean, C)
				C get y()
				get z()
				set a(
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoNPE_05() {
		val script = '''
			class C {}
			function f( ~C with {
				s: string;
				n;
				f1(string)
				void f2()
				number f3(boolean, C)
				C get y()
				get z()
				set a()
			} p) {}
		'''.parse
		analyser.analyse(script, "script", "script")
	}
	
	@Test
	def void testNoNPE_06() {
		val script = '''mport * as 1 from 'a/X';
			var N1.x: X;'''.parse
		analyser.analyse(script, "script", "script");
	}

	@Test
	def void testNoCyclicResolution_01() {
		val script = '''
			function(a){
				return a.b
			}
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalArgumentException_01() {
		val script = '''
			interface R1 {
				void f(p: number) {}
			}
			interface R2 {
				void f(p: string) {
			class C implements R1, R2 {}
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalArgumentException_02() {
		val script = '''
			interface R1 {
				x: string;
			}
			class S {
				void x() {
			class C1 extends S implements R1 {}
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalStateException_01() {
		val script = '''
			var fo = 5,/*
						*/
						class A { a = new A(); }
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalStateException_02() {
		val script = '''
			public class C {
				f0;
				private f1;
				project
				@Internal protected f4;
				protected f5;
				@Internal public f6;
				public f7;
			}
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalArgumentException_03() {
		val script = '''
			import * as N2 from 'a/X';
			import * as 1 from 'a/X';
			var N1.X x;
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalArgumentException_04() {
		val script = '''
			import X1 from 'a/X';
			import X2 from 'a/X';
			var X1 x = X2;
		'''.parse
		analyser.analyse(script, "script", "script")
	}

	@Test
	def void testNoIllegalStateException_03() {
		val script = '''
			var ol = {
				et target() {return null}
			}
			ol.target=null;
		'''.parse
		analyser.analyse(script, "script", "script")
	}

}
